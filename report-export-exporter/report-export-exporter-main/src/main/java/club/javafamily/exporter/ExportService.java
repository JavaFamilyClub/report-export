package club.javafamily.exporter;

import club.javafamily.assembly.Assembly;
import club.javafamily.enums.ExportType;
import club.javafamily.utils.collections.CollectionUtil;
import club.javafamily.utils.common.MessageException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jack Li
 * @date 2022/9/9 下午2:20
 * @description
 */
@Slf4j
public class ExportService {

   private List<Exporter> exporters;

   public void export(Assembly<?> assembly, ExportType type) {
      createExporter(type);


   }

   private Exporter createExporter(ExportType type) {
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
