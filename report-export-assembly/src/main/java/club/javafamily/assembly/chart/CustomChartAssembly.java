package club.javafamily.assembly.chart;

import java.awt.image.BufferedImage;

/**
 * @author Jack Li
 * @date 2022/9/9 下午1:55
 * @description
 */
public abstract class CustomChartAssembly extends ChartAssembly {

   /**
    * 自定义 Chart
    * @return chart 图
    */
   public abstract BufferedImage createImage();

}
