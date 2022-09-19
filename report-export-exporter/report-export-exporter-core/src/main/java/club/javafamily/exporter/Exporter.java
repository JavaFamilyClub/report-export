package club.javafamily.exporter;

import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Jack Li
 * @date 2022/9/7 上午10:45
 * @description Assembly 导出器
 */
public interface Exporter {

    /**
     * 导出器是否支持该类型导出
     * @param exportType 导出类型
     * @return boolean
     */
    default boolean accepted(ExportType exportType) {
        return supportedTypes().contains(exportType);
    }

    /**
     * 导出器支持的导出类型
     * @return list
     */
    List<ExportType> supportedTypes();

    /**
     * 准备导出
     * @param assembly assembly
     * @param out stream
     */
    void prepareExport(Assembly<?> assembly, OutputStream out);

    /**
     * 导出报表
     * @param reportSheet ReportSheet
     */
    void exportReport(ReportSheet reportSheet) throws Exception;

    /**
     * 导出表格
     * @param assembly TableAssembly
     */
    void exportTable(TableAssembly assembly) throws Exception;

    /**
     * 导出 Chart
     * @param assembly ChartAssembly
     */
    void exportChart(ChartAssembly assembly);

    /**
     * 导出 Image
     * @param assembly ImageAssembly
     */
    void exportImage(ImageAssembly assembly);

    /**
     * 导出 Text
     * @param assembly TextAssembly
     */
    void exportText(TextAssembly assembly) throws Exception;

    /**
     * 导出完成
     * @throws Exception when failed
     */
    void completeExport() throws Exception;

    /**
     * 按类型执行导出
     * @param assembly assembly
     * @throws Exception export failed
     */
    default void export(Assembly<?> assembly) throws Exception {
        if(assembly instanceof ReportSheet) {
            this.exportReport((ReportSheet) assembly);
        }
        else if(assembly instanceof TableAssembly) {
            this.exportTable((TableAssembly) assembly);
        }
        else if(assembly instanceof ChartAssembly) {
            this.exportChart((ChartAssembly) assembly);
        }
        else if(assembly instanceof ImageAssembly) {
            this.exportImage((ImageAssembly) assembly);
        }
        else if(assembly instanceof TextAssembly) {
            this.exportText((TextAssembly) assembly);
        }
        else {
            this.completeExport();
            throw new UnsupportedOperationException("未知的 Assembly 类型!");
        }
    }
}
