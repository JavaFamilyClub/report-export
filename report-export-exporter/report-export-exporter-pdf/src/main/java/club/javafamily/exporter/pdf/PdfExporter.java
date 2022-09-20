package club.javafamily.exporter.pdf;

import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.report.style.ReportSheetStyleLayout;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.table.style.TableStyleLayout;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.assembly.text.lens.TextTableLens;
import club.javafamily.assembly.text.style.TextStyleLayout;
import club.javafamily.common.DoublePoint;
import club.javafamily.common.FloatInsets;
import club.javafamily.enums.ExportType;
import club.javafamily.exporter.AbstractExporter;
import club.javafamily.exporter.pdf.utils.PdfUtils;
import club.javafamily.lens.TableLens;
import club.javafamily.style.StyleLayout;
import club.javafamily.utils.Tool;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.FixedDashedBorder;
import com.itextpdf.layout.borders.InsetBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author Jack Li
 * @date 2022/9/8 下午5:50
 * @description Pdf 导出
 */
public class PdfExporter extends AbstractExporter {

    /**
     * pdf doc
     */
    private Document document;

    @Override
    public List<ExportType> supportedTypes() {
        return Collections.singletonList(ExportType.PDF);
    }

    @Override
    public void prepareExport(Assembly<?> assembly, OutputStream out) {
        super.prepareExport(assembly, out);

        if(!(assembly instanceof ReportSheet)) {
            return;
        }

        ReportSheet report = (ReportSheet) assembly;

        // 创建一个指向文件或者 out 流的 PDFWriter, 该 writer 会监听 PdfDocument
        PdfWriter pdfWriter = new PdfWriter(out);
        // 创建 PdfDocument 代表要创建的 PDF 文件, 管理要写入的内容和相关信息
        PdfDocument pdf = new PdfDocument(pdfWriter);
        PageSize pageSize = new PageSize(report.getWidth(), report.getHeight());
        pageSize.setX(report.getX())
                .setY(report.getY());

        // 创建一个文档, 代表 PDF 的一页
//        final PdfPage currentPage = pdf.addNewPage(pageSize);

        document = new Document(pdf, pageSize);

        ReportSheetStyleLayout styleLayout = report.getStyleLayout();

        if(styleLayout == null) {
            return;
        }

        FloatInsets pageInsets = styleLayout.getPageInsets();

        if(pageInsets != null) {
            document.setMargins(pageInsets.top, pageInsets.right, pageInsets.bottom, pageInsets.left);
        }
    }

    @Override
    public void exportTable(TableAssembly assembly) throws Exception {
        assert document != null;

        TableLens tableLens = assembly.getTableLens();
        int colCount = tableLens.getColCount();

        writeTitle(document, assembly);

        Table table = new Table(colCount);

        // 指定 table 相对 Document 的宽度.
        // <code>UnitValue.createPercentValue(100)</code> 表示 100%(排除页边距).
        table.setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 填充 table
        for(int i = 0; i < tableLens.getRowCount(); i++) {
            table.startNewRow();

            for(int j = 0; j < colCount; j++) {
                fillCellData(table, assembly, i, j);
            }
        }

        if(assembly.getHeight() >= 0) {
            table.setHeight(assembly.getHeight());
        }

        DoublePoint position = getAbsolutePosition(assembly);
//        table.setFixedPosition((float) position.getX(),
//                PdfUtils.pxToPt((float) position.getY()),
//                assembly.getWidth()
//        );
//        useAbsolute(table);
        table.setFixedPosition((float) position.getX(),
                (float) position.getY(),
                UnitValue.createPercentValue(100));
        useAbsolute(table);


        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 将 table 写入 Document
        document.add(table);
    }

    public static void writeTitle(Document doc, Assembly assembly) throws Exception {
        if (assembly.getTitle() == null) {
            return;
        }

        Paragraph p = new Paragraph(assembly.getTitle());
        final StyleLayout styleLayout = assembly.getStyleLayout();
        Font titleFont = styleLayout.getTitleFont();
        final Color titleFontColor = styleLayout.getTitleFontColor();

        p.setFont(PdfUtils.convertFont(titleFont))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        if(titleFont != null) {
             p.setFontSize(titleFont.getSize());
        }

        if(titleFontColor != null) {
            p.setFontColor(PdfUtils.convertColor(titleFontColor));
        }

        doc.add(p);
    }

    /**
     * fill cell data.
     * @param table pdf table
     * @param assembly table
     * @param row row index
     * @param col col index
     */
    private void fillCellData(Table table, TableAssembly assembly, int row, int col) throws Exception {
        TableLens tableLens = assembly.getTableLens();
        TableStyleLayout styleLayout = assembly.getStyleLayout();
        boolean isHeader = tableLens.isRowHeader(row);
        Object cell = tableLens.getObject(row, col);

        Font cellFont = styleLayout.getFont(row, col);
        Color cellBG = styleLayout.getBackground(row, col);
        PdfFont pdfFont = PdfUtils.convertFont(cellFont);

        // <code>Paragraph</code> 代表一个段落, 传入字符串就可以写入一个文本段落到 Document,
        // 传入 <code>Cell</code> 就会画一个 cell.
        Paragraph text = new Paragraph(Tool.toString(cell));
        text.setFont(pdfFont);

        // create cell
        Cell pdfCell = new Cell().add(text);

        pdfCell.setHeight(styleLayout.getHeight(row, col));

        pdfCell.setFont(pdfFont)
                .setBorder(new SolidBorder(new DeviceRgb(Color.BLACK), 0.5f))
                .setBackgroundColor(PdfUtils.convertColor(cellBG));

        if(isHeader) {
            table.addHeaderCell(pdfCell);
        }
        else {
            table.addCell(pdfCell);
        }
    }

    @Override
    public void exportChart(ChartAssembly assembly) {
        assert document != null;
    }

    @Override
    public void exportImage(ImageAssembly assembly) {
        assert document != null;

        ImageData imageData = ImageDataFactory.create(assembly.getImgData());

        Image image = new Image(imageData);

        image.setWidth(assembly.getWidth());
        image.setHeight(assembly.getHeight());

        DoublePoint position = getAbsolutePosition(assembly);
        image.setFixedPosition((float) position.getX(), (float) position.getY());
        useAbsolute(image);

        document.add(image);
    }

    /**
     * 使用绝对定位
     * @param container container
     */
    private void useAbsolute(IPropertyContainer container) {
        container.setProperty(Property.POSITION, LayoutPosition.ABSOLUTE);
    }

    /**
     * 转化为绝对定位
     * @param assembly assembly
     * @return 绝对定位的位置
     */
    private DoublePoint getAbsolutePosition(Assembly assembly) {
        ReportSheetStyleLayout reportStyleLayout = reportSheet.getStyleLayout();
        float height = assembly.getHeight();

        height = height > 0 ? height : 0;

        return new DoublePoint(PdfUtils.pxToPt((float) assembly.getPosition().getX()),
                reportSheet.getHeight() - reportStyleLayout.getBottom()
                        - (float) assembly.getPosition().getY() - height);
    }

    @Override
    public void exportText(TextAssembly assembly) throws Exception {
        assert document != null;

        TextTableLens lens = assembly.getTextTableLens();
        TextStyleLayout styleLayout = assembly.getStyleLayout();

        Font cellFont = styleLayout.getTextFont();
//        Color cellBG = styleLayout.getBackground(row, col);
        PdfFont pdfFont = PdfUtils.convertFont(cellFont);

        Paragraph text = new Paragraph(lens.getText());
        text.setFont(pdfFont);
        text.setFontColor(PdfUtils.convertColor(styleLayout.getTextColor()));

        DoublePoint position = getAbsolutePosition(assembly);
        text.setFixedPosition((float) position.getX(), (float) position.getY(),
                UnitValue.createPercentValue(100));
        useAbsolute(text);

        document.add(text);
    }

    @Override
    public void completeExport() throws Exception {
        super.completeExport();
        document.close();
    }
}
