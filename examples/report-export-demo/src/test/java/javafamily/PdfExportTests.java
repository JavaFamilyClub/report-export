package javafamily;

import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.report.style.ReportSheetStyleLayout;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.table.style.DefaultTableStyleLayout;
import club.javafamily.assembly.table.style.TableStyleLayout;
import club.javafamily.common.DoublePoint;
import club.javafamily.exporter.pdf.PdfExporter;
import club.javafamily.lens.DefaultTableLens;
import club.javafamily.lens.TableLens;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class PdfExportTests {

    private TableLens lens;
    private TableAssembly table;
    private ImageAssembly image;
    private ReportSheet reportSheet;

    @BeforeEach
    void init() throws Exception {
        initTableLens();
        initTable();
        initImage();
        initReport();
    }

    @Test
    void testExport() throws Exception {
        FileOutputStream out = new FileOutputStream("./target/demo.pdf");

        try(PdfExporter pdfExporter = new PdfExporter()) {
            pdfExporter.prepareExport(reportSheet, out);
            pdfExporter.export(reportSheet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Export Success!");
    }

    private void initTableLens() {
        Object[][] data = new Object[5][5];

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                if(row == 0) {
                    data[row][col] = "Header " + col;
                }
                else {
                    data[row][col] = "第 " + row + " 行, 第 " + col + " 列";
//                    data[row][col] = "r " + row + ", c " + col + "";
                }
            }
        }

        lens = new DefaultTableLens(data);
    }

    private void initReport() {
        ReportSheetStyleLayout reportSheetStyleLayout = new ReportSheetStyleLayout();
        reportSheet = new ReportSheet();
        reportSheet.setStyleLayout(reportSheetStyleLayout);

        reportSheet.addAssembly(table);
        reportSheet.addAssembly(image);
    }

    private void initTable() {
        TableStyleLayout tableStyleLayout = new DefaultTableStyleLayout();

        tableStyleLayout.setBackground(3, 3, Color.RED);

        tableStyleLayout.setRowBackground(0, Color.lightGray);

        tableStyleLayout.setColBackground(2, Color.pink);
        tableStyleLayout.setColBackground(4, Color.ORANGE);

        table = new TableAssembly();
        table.setLens(lens);
        table.setStyleLayout(tableStyleLayout);

        table.setPosition(new DoublePoint(5, 500));
    }

    private void initImage() throws IOException {
        image = new ImageAssembly();

        ClassPathResource resource = new ClassPathResource("icon.ico");
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
        image.setImgData(bytes);

        image.setPosition(new DoublePoint(5, 5));
        image.setWidth(50);
        image.setHeight(50);
    }

}
