package club.javafamily.graph;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.common.Order;

import java.awt.image.BufferedImage;

/**
 * @author Jack Li
 * @date 2022/9/7 下午1:53
 * @description
 */
public interface ChartGraph extends Order {

   /**
    * 是否支持绘制 Chart
    * @param chart chart
    * @return bool
    */
   boolean supported(ChartAssembly chart);

   /**
    * 绘制 Chart
    * @param chart Assembly
    * @return image
    */
   BufferedImage drawChart(ChartAssembly chart);

   /**
    * 启用顺序, 越小优先级越高
    * 当有多个 {@link ChartGraph} 都满足 {@link #supported} is <code>true</code>时,
    * 由 order 决定使用那个绘制
    * @return order
    */
   @Override
   default int getOrder() {
      return INTERNAL_PRIORITY;
   }
}
