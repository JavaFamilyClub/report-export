package club.javafamily.exporter;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;

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
     * 导出报表
     * @param reportSheet ReportSheet
     * @param out stream
     */
    void exportReport(ReportSheet reportSheet, OutputStream out);

    /**
     * 导出表格
     * @param assembly TableAssembly
     * @param out stream
     */
    void exportTable(TableAssembly assembly, OutputStream out);

    /**
     * 导出 Chart
     * @param assembly ChartAssembly
     * @param out stream
     */
    void exportChart(ChartAssembly assembly, OutputStream out);

    /**
     * 导出 Image
     * @param assembly ImageAssembly
     * @param out stream
     */
    void exportImage(ImageAssembly assembly, OutputStream out);

    /**
     * 导出 Text
     * @param assembly TextAssembly
     * @param out stream
     */
    void exportText(TextAssembly assembly, OutputStream out);
}
