package club.javafamily.exporter;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.enums.ExportType;

import java.io.OutputStream;
import java.util.List;

public interface Exporter {

    List<ExportType> supportedTypes();

    default boolean accepted(ExportType exportType) {
        return supportedTypes().contains(exportType);
    }

    void exportTable(TableAssembly assembly, OutputStream out);

    void exportChart(ChartAssembly assembly, OutputStream out);

    void exportImage(ImageAssembly assembly, OutputStream out);

    void exportText(TextAssembly assembly, OutputStream out);
}
