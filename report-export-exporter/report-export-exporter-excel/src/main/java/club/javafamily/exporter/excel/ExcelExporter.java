package club.javafamily.exporter.excel;

import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;
import club.javafamily.exporter.AbstractExporter;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class ExcelExporter extends AbstractExporter {

    @Override
    public List<ExportType> supportedTypes() {
        return Arrays.asList(ExportType.Excel, ExportType.Excel_2003);
    }

    @Override
    public void exportReport(ReportSheet reportSheet) {

    }

    @Override
    public void exportTable(TableAssembly assembly) {

    }

    @Override
    public void exportChart(ChartAssembly assembly) {

    }

    @Override
    public void exportImage(ImageAssembly assembly) {

    }

    @Override
    public void exportText(TextAssembly assembly) {

    }

}
