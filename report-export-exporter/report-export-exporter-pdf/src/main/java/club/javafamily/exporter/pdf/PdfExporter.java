package club.javafamily.exporter.pdf;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;
import club.javafamily.exporter.Exporter;

import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public class PdfExporter implements Exporter {

    @Override
    public List<ExportType> supportedTypes() {
        return Collections.singletonList(ExportType.PDF);
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
