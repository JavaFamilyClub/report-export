package club.javafamily.exporter.excel;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;
import club.javafamily.exporter.Exporter;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class ExcelExporter implements Exporter {

    @Override
    public List<ExportType> supportedTypes() {
        return Arrays.asList(ExportType.Excel, ExportType.Excel_2003);
    }

    @Override
    public void exportReport(ReportSheet reportSheet, OutputStream out) {

    }

    @Override
    public void exportTable(TableAssembly assembly, OutputStream out) {

    }

    @Override
    public void exportChart(ChartAssembly assembly, OutputStream out) {

    }

    @Override
    public void exportImage(ImageAssembly assembly, OutputStream out) {

    }

    @Override
    public void exportText(TextAssembly assembly, OutputStream out) {

    }
}
