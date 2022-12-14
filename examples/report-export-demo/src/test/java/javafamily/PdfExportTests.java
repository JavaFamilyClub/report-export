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
import club.javafamily.common.PageSize;
import club.javafamily.constants.StyleLayoutConstants;
import club.javafamily.exporter.pdf.PdfExporter;
import club.javafamily.lens.DefaultTableLens;
import club.javafamily.lens.TableLens;
import club.javafamily.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

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

   private TextAssembly auth;

   @Test
   void testExport() throws Exception {
      File file = new File("./target/demo.pdf");

      if(file.exists()) {
         file.delete();
      }

      FileOutputStream out = new FileOutputStream(file);

      try(PdfExporter pdfExporter = new PdfExporter()) {
         pdfExporter.prepareExport(reportSheet, out);
         pdfExporter.export(reportSheet);
      }
      catch (Exception e) {
         e.printStackTrace();
      }

      log.info("Export Success!");
   }

   @BeforeEach
   void init() throws Exception {
//      initTableLens();

      initTableLensWithSpan();

      initReport();

      initTable();
      initImage();

      initText();

      reportSheet.addAssembly(image);

      reportSheet.addAssembly(unit);
      reportSheet.addAssembly(unitEn);
      reportSheet.addAssembly(projectName);
      reportSheet.addAssembly(pointPosition);
      reportSheet.addAssembly(publishTime);

      reportSheet.addAssembly(table);

      reportSheet.addAssembly(auth);
   }

   private void initTableLens() throws Exception {
      ClassPathResource resource = new ClassPathResource("data_Sep20_H17.txt");

      List<Object[]> data = new ArrayList<>();

      try(InputStream in = resource.getInputStream();
          InputStreamReader isr = new InputStreamReader(in);
          BufferedReader br = new BufferedReader(isr))
      {
         br.lines().forEach(line -> {
            String[] items = line.split(",");

            if(data.size() > 0) {
               Date time = DateUtil.parseDateTime(items[0]);
               items[0] = time.getHours() + "";
            }

            data.add(items);
         });
      }

      lens = new DefaultTableLens(data);
   }

   private void initTableLensWithSpan() throws Exception {
      ClassPathResource resource = new ClassPathResource("data2_Sep20_H17.txt");

      List<Object[]> data = new ArrayList<>();
      final SimpleDateFormat format = new SimpleDateFormat(DateUtil.NORMAL_DATE_HOUR_PATTERN);

      try(InputStream in = resource.getInputStream();
          InputStreamReader isr = new InputStreamReader(in);
          BufferedReader br = new BufferedReader(isr))
      {
         br.lines().forEach(line -> {
            String[] items = line.split(",");

            if(data.size() > 1) {
               Date time = null;
               try {
                  time = format.parse(items[0]);
               } catch (ParseException e) {
                  throw new RuntimeException(e);
               }
               items[0] = time.getHours() + "";
            }

            data.add(items);
         });
      }

      lens = new DefaultTableLens(data, 2, 1);

      // row count span
      lens.setRowSpan(0, 0, 2);

      // ???
      lens.setColSpan(0, 1, 6);

      // ?????????
      lens.setColSpan(0, 7, 3);

      // ??????
      lens.setColSpan(0, 10, 2);

      // ??????
      lens.setColSpan(0, 12, 3);

      // ???
      lens.setColSpan(0, 16, 2);
   }

   private void initMockTableLens() throws Exception {
      Object[][] data = new Object[5][5];

      for (int row = 0; row < data.length; row++) {
         for (int col = 0; col < data[row].length; col++) {
            if(row == 0) {
               data[row][col] = "Header " + col;
            }
            else {
               data[row][col] = "??? " + row + " ???, ??? " + col + " ???";
            }
         }
      }

      lens = new DefaultTableLens(data);
   }

   private void initReport() {
      ReportSheetStyleLayout reportSheetStyleLayout = new ReportSheetStyleLayout();
      reportSheet = new ReportSheet();
      reportSheet.setStyleLayout(reportSheetStyleLayout);

      // rotate
      reportSheet.setPageSize(PageSize.A4.rotate());
   }

   private void initTable() {
      TableStyleLayout tableStyleLayout = new DefaultTableStyleLayout();

      for (int i = lens.getHeaderRowCount(); i < lens.getRowCount(); i++) {
         if(Double.parseDouble((String) lens.getObject(i, 6)) > 20) {
            tableStyleLayout.setBackground(i, 6, Color.RED);
         }

         if(Double.parseDouble((String) lens.getObject(i, 17)) > 1) {
            tableStyleLayout.setBackground(i, 17, Color.RED);
         }
      }

      final Color rowHeaderBg = new Color(242, 193, 97);
      tableStyleLayout.setRowBackground(0, rowHeaderBg);
      tableStyleLayout.setRowBackground(1, rowHeaderBg);

      tableStyleLayout.setColBackground(2, new Color(237, 220, 218));
      tableStyleLayout.setColBackground(4, new Color(222, 230, 240));

      tableStyleLayout.setColBackground(7, new Color(250, 240, 218));
      tableStyleLayout.setColBackground(8, new Color(250, 240, 218));
      tableStyleLayout.setColBackground(9, new Color(250, 240, 218));
      tableStyleLayout.setColBackground(10, new Color(250, 240, 218));

      tableStyleLayout.setColBackground(14, new Color(222, 230, 240));
      tableStyleLayout.setColBackground(18, new Color(237, 220, 218));

      table = new TableAssembly();
      table.setLens(lens);
      table.setStyleLayout(tableStyleLayout);

      table.setPosition(new DoublePoint(0, 180));

      table.setWidth((float) (reportSheet.getWidth()));
   }

   private void initImage() throws IOException {
      image = new ImageAssembly();

      ClassPathResource resource = new ClassPathResource("icon.ico");
      byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
      image.setImgData(bytes);

      image.setPosition(new DoublePoint(0, 10));
      image.setWidth(80);
      image.setHeight(80);
   }

   private void initText() {
      unit = new TextAssembly("????????????????????????");
      TextStyleLayout styleLayout1 = unit.getStyleLayout();
      styleLayout1.setTextColor(new Color(17,125,204));

      unit.setPosition(new DoublePoint(80, 45));

      unitEn = new TextAssembly("Mlog Sea Marine Forecasting Center, SOA");
      TextStyleLayout styleLayout2 = unitEn.getStyleLayout();
      styleLayout2.setTextColor(new Color(17,125,204));
      styleLayout2.setTextFont(StyleLayoutConstants.DEFAULT_HEADER_FONT);

      unitEn.setPosition(new DoublePoint(80, 65));

      projectName = new TextAssembly("\"???????????????\"????????????????????????");

      projectName.setPosition(new DoublePoint(20, 110));

      TextStyleLayout styleLayout = projectName.getStyleLayout();
      styleLayout.setTextFont(StyleLayoutConstants.DEFAULT_HEADER_FONT);
      styleLayout.setTextColor(Color.black);

      pointPosition = new TextAssembly("????????????: 120??52???E, 27??21???N");

      pointPosition.setPosition(new DoublePoint(20, 140));

      pointPosition.setStyleLayout(styleLayout);

      publishTime = new TextAssembly("????????????: 2022-09-20, 17??? ");

      publishTime.setPosition(new DoublePoint(500 , 140));

      publishTime.setStyleLayout(styleLayout);

      auth = new TextAssembly("-- Mlog ????????????");

      auth.setPosition(new DoublePoint(650, 520));

      auth.setStyleLayout(styleLayout);
   }

}
