package club.javafamily.assembly.chart.plot;

import club.javafamily.constants.StyleLayoutConstants;
import lombok.Data;

import java.awt.*;

/**
 * @author Jack Li
 * @date 2022/9/8 下午1:52
 * @description
 */
@Data
public class PlotInfo implements StyleLayoutConstants {

   private Color background = DEFAULT_BG;

   private Font font = DEFAULT_TEXT_FONT;

   private boolean hGridLine;
   private boolean vGridLine;
   private Color hGridLineColor = Color.lightGray;
   private Color vGridLineColor = Color.LIGHT_GRAY;

}
