package club.javafamily.exporter.pdf.utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.awt.*;
import java.io.IOException;

/**
 * @author Jack Li
 * @date 2022/9/14 上午9:09
 * @description
 */
public class PdfUtils {

   /**
    * Convert awt's color to iText's color.
    * @param color awt's color.
    * @return iText's color
    */
   public static DeviceRgb convertColor(java.awt.Color color) {
      return new DeviceRgb(color);
   }

   /**
    * Convert awt's font to iText's font.
    * @param font awt's font.
    * @return iText's font
    */
   public static PdfFont convertFont(Font font) throws IOException {
      // 这在项目打成 jar 包后会出现访问不到字体文件的错误.
//      try {
//         PdfFont pdfFont = PdfFontFactory.createRegisteredFont(font.getName());
//
//         return pdfFont;
//      } catch(IOException e) {
//         LOGGER.warn("Registered font is not found! {}", font.getFontName());
//      }

      return PdfFontFactory.createFont(font.getFontName());
   }
}
