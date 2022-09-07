package club.javafamily.graph.line;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.enums.ChartType;
import club.javafamily.graph.ChartGraph;
import club.javafamily.graph.utils.GraphUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import java.awt.image.BufferedImage;

/**
 * @author Jack Li
 * @date 2022/9/7 下午3:58
 * @description
 */
public class LineChartGraph implements ChartGraph {
   @Override
   public boolean supported(ChartAssembly chart) {
      return chart.getChartType() == ChartType.XY_LINE.ordinal();
   }

   @Override
   public BufferedImage drawChart(ChartAssembly assembly) {
      final XYDataset dataset = (XYDataset) GraphUtils.createDataset(assembly);

      JFreeChart chart = ChartFactory.createXYLineChart(assembly.getTitle(),
         assembly.primaryXAxis().getLabel(),
         assembly.primaryYAxis().getLabel(),
         dataset, PlotOrientation.VERTICAL,
         true, true, false);

      XYPlot plot = (XYPlot) chart.getPlot();

      return chart.createBufferedImage((int) assembly.getWidth(),
         (int) assembly.getHeight());
   }
}
