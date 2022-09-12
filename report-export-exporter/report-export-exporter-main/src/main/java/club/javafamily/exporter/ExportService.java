package club.javafamily.exporter;

import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;
import club.javafamily.exporter.excel.ExcelExporter;
import club.javafamily.exporter.pdf.PdfExporter;
import club.javafamily.exporter.report.ReportExporter;
import club.javafamily.utils.collections.CollectionUtil;
import club.javafamily.utils.common.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jack Li
 * @date 2022/9/9 下午2:20
 * @description
 */
@Slf4j
@Service
public class ExportService {

   private List<Exporter> exporters;
   private Map<ExportType, Class<? extends Exporter>> exporterMap = new HashMap<>();

   public ExportService(List<Exporter> exporters) {
      this.exporters = exporters;

      exporterMap.put(ExportType.PDF, PdfExporter.class);
      exporterMap.put(ExportType.Excel, ExcelExporter.class);
      exporterMap.put(ExportType.Excel_2003, ExcelExporter.class);
   }

   /**
    * Assembly 导出入口
    * @param assembly assembly
    * @param type 导出类型
    */
   public void export(Assembly<?> assembly, ExportType type, OutputStream out) throws Exception {
      Exporter exporter = createExporter(type);

//      AssemblyExporter assemblyExporter = createAssemblyExporter(assembly, exporter);
//
//      assemblyExporter.prepareExport();
//      assemblyExporter.export();
//      assemblyExporter.completeExport();

      exporter.prepareExport(assembly, out);

      exporter.export(assembly);

      exporter.completeExport();
   }

   /**
    * 创建 Assembly 导出器
    * @param assembly assembly
    * @param exporter exporter
    * @return AssemblyExporter
    */
   public static AssemblyExporter createAssemblyExporter(Assembly<?> assembly, Exporter exporter) {
      AssemblyExporter assemblyExporter;

      if(assembly instanceof ReportSheet) {
         assemblyExporter = new ReportExporter(exporter, assembly);
      }
      else {
         assemblyExporter =  new GeneralAssemblyExporter(exporter, assembly);
      }

      return assemblyExporter;
   }

   /**
    * 寻找导出器
    * @param type 类型
    * @return Exporter
    */
   private Exporter createExporter(ExportType type) throws Exception {
      Class<? extends Exporter> clazz = exporterMap.get(type);

      if(clazz == null) {
         throw new MessageException("不支持的导出类型: " + type);
      }

      return clazz.newInstance();
   }

   /**
    * 寻找导出器
    * @param type 类型
    * @return Exporter
    */
   private Exporter findExporter(ExportType type) {
      final List<Exporter> availableExporters = exporters.stream()
         .filter(exporter -> exporter.accepted(type))
         .collect(Collectors.toList());

      if(CollectionUtil.isEmpty(availableExporters)) {
         throw new MessageException("不支持的导出类型: " + type);
      }

      if(availableExporters.size() > 1) {
         log.warn("{} 匹配多个 Exporter", type);
      }

      return availableExporters.get(0);
   }

}
