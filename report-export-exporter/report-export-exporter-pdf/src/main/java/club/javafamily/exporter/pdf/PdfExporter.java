package club.javafamily.exporter.pdf;

import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.image.ImageAssembly;
import club.javafamily.assembly.report.ReportSheet;
import club.javafamily.assembly.report.style.ReportSheetStyleLayout;
import club.javafamily.assembly.table.TableAssembly;
import club.javafamily.assembly.text.TextAssembly;
import club.javafamily.common.FloatInsets;
import club.javafamily.enums.ExportType;
import club.javafamily.exporter.AbstractExporter;
import club.javafamily.exporter.pdf.utils.PdfUtils;
import club.javafamily.lens.TableLens;
import club.javafamily.style.StyleLayout;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.awt.*;
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
        final PdfPage currentPage = pdf.addNewPage(pageSize);

        document = new Document(pdf, pageSize);

        ReportSheetStyleLayout styleLayout = report.getStyleLayout();
        FloatInsets pageInsets = styleLayout.getPageInsets();

        document.setMargins(pageInsets.top, pageInsets.right, pageInsets.bottom, pageInsets.left);
    }

    @Override
    public void exportTable(TableAssembly assembly) throws Exception {
        assert document != null;

        TableLens tableLens = assembly.getTableLens();
        int colCount = tableLens.getColCount();

        writeTitle(document, assembly);

        Table table = new Table(colCount);


    }

    public static void writeTitle(Document doc, Assembly assembly) throws Exception {
        Paragraph p = new Paragraph(assembly.getTitle());
        final StyleLayout styleLayout = assembly.getStyleLayout();
        Font titleFont = styleLayout.getTitleFont();
        final Color titleFontColor = styleLayout.getTitleFontColor();

        p.setFont(PdfUtils.convertFont(titleFont))
           .setFontSize(titleFont.getSize())
           .setTextAlignment(TextAlignment.CENTER)
           .setHorizontalAlignment(HorizontalAlignment.CENTER)
           .setFontColor(PdfUtils.convertColor(titleFontColor));

        doc.add(p);
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

        document.add(image);
    }

    @Override
    public void exportText(TextAssembly assembly) {
        assert document != null;
    }

}
