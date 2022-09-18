package club.javafamily.exporter.pdf.utils;

import club.javafamily.constants.StyleLayoutConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.FontStyles;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jack Li
 * @date 2022/9/14 上午9:09
 * @description
 */
public class PdfUtils {

   private PdfUtils() {
   }

   static {
      PdfFontFactory.registerSystemDirectories();
   }

   /**
    * Convert awt's color to iText's color.
    * @param color awt's color.
    * @return iText's color
    */
   public static com.itextpdf.kernel.colors.Color convertColor(java.awt.Color color) {
      if(color == null) {
         color = StyleLayoutConstants.WHITE;
      }

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

      if(font == null) {
         font = StyleLayoutConstants.DEFAULT_TEXT_FONT;
      }

      PdfFont pdfFont = PdfFontFactory.createRegisteredFont(font.getName(), PdfEncodings.IDENTITY_H,
              PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED,
              font.isBold() ? FontStyles.BOLD : FontStyles.NORMAL);

      return pdfFont;
   }

   /**
    * convert px to pt
    * @param px px
    * @return pt
    */
   public static float pxToPt(float px) {
      return px * 3 / 4;
   }
}
