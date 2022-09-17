package javafamily;

import club.javafamily.exporter.pdf.PdfExporter;
import club.javafamily.lens.DefaultTableLens;
import club.javafamily.lens.TableLens;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class PdfExportTests {

    TableLens lens;

    @Before
    void init() {
        lens = new DefaultTableLens();
    }

    @Test
    void testExport() {
        PdfExporter pdfExporter = new PdfExporter();

    }

}
