package club.javafamily.graph.timeseries;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.enums.ChartType;
import club.javafamily.graph.ChartGraph;

import java.awt.image.BufferedImage;

/**
 * @author Jack Li
 * @date 2022/9/7 下午3:06
 * @description
 */
public class TimeSeriesChartGraph implements ChartGraph {

   @Override
   public boolean supported(ChartAssembly chart) {
      return chart.getChartType() == ChartType.TIME_SERIES.ordinal();
   }

   @Override
   public BufferedImage drawChart(ChartAssembly chart) {
      return null;
   }
}
