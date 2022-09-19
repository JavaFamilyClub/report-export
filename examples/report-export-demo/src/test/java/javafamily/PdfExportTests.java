package javafamily;

import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.report.style.ReportSheetStyleLayout;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.table.style.DefaultTableStyleLayout;
import club.javafamily.assembly.table.style.TableStyleLayout;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.assembly.text.style.TextStyleLayout;
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

    private TextAssembly unit;
    private TextAssembly unitEn;

    private TextAssembly projectName;
    private TextAssembly pointPosition;
    private TextAssembly publishTime;

    @BeforeEach
    void init() throws Exception {
        initTableLens();

        initReport();

        initTable();
        initImage();

        initText();

        reportSheet.addAssembly(table);
        reportSheet.addAssembly(image);

        reportSheet.addAssembly(unit);
        reportSheet.addAssembly(unitEn);
        reportSheet.addAssembly(projectName);
        reportSheet.addAssembly(pointPosition);
        reportSheet.addAssembly(publishTime);
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
                }
            }
        }

        lens = new DefaultTableLens(data);
    }

    private void initReport() {
        ReportSheetStyleLayout reportSheetStyleLayout = new ReportSheetStyleLayout();
        reportSheet = new ReportSheet();
        reportSheet.setStyleLayout(reportSheetStyleLayout);
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

        table.setPosition(new DoublePoint(10, 140));

        table.setHeight(120);
        table.setWidth((float) (reportSheet.getWidth()));
    }

    private void initImage() throws IOException {
        image = new ImageAssembly();

        ClassPathResource resource = new ClassPathResource("icon.ico");
        byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
        image.setImgData(bytes);

        image.setPosition(new DoublePoint(0, 0));
        image.setWidth(80);
        image.setHeight(80);
    }

    private void initText() {
        unit = new TextAssembly("JavaFamily 海洋预报中心");
        TextStyleLayout styleLayout1 = unit.getStyleLayout();
        styleLayout1.setTextColor(new Color(102, 204, 255));

        unit.setPosition(new DoublePoint(90, 0));

        unitEn = new TextAssembly("JavaFamily Sea Marine Forecasting Center, SOA");
        unitEn.setStyleLayout(styleLayout1);

        unitEn.setPosition(new DoublePoint(90, 0));

        projectName = new TextAssembly("\"神泉二项目\"施工海域海洋预报");

        projectName.setPosition(new DoublePoint(0, 90));

        pointPosition = new TextAssembly("预报点位: 120°52′E, 27°21′N");

        pointPosition.setPosition(new DoublePoint(0, 120));

        publishTime = new TextAssembly("发布时间: 2022-05-13, 08时 ");

        publishTime.setPosition(new DoublePoint(500 , 120));
    }


}
