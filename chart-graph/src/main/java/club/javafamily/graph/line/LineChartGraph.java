package club.javafamily.graph.line;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.chart.plot.PlotInfo;
import club.javafamily.enums.ChartType;
import club.javafamily.graph.ChartGraph;
import club.javafamily.graph.utils.GraphUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
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
      final XYDataset dataset = GraphUtils.createXYDataset(assembly);

      JFreeChart chart = ChartFactory.createXYLineChart(assembly.getTitle(),
         assembly.primaryX1Axis().getTitle(),
         assembly.primaryY1Axis().getTitle(),
         dataset, PlotOrientation.VERTICAL,
         assembly.getLegendInfo().isVisible(),
         assembly.getTooltipInfo().isVisible(),
         false);

      initPlot((XYPlot) chart.getPlot(), assembly.getPlotInfo());
      initLegend(chart.getLegend());

      return chart.createBufferedImage((int) assembly.getWidth(),
         (int) assembly.getHeight());
   }

   private void initLegend(LegendTitle legend) {
   }

   private void initPlot(XYPlot plot, PlotInfo plotInfo) {
      plot.setBackgroundPaint(plotInfo.getBackground());

      plot.setOutlineVisible(false);

      if(plotInfo.isVGridLine()) {
         plot.setDomainGridlinePaint(plotInfo.getVGridLineColor());
      }

      if(plotInfo.isHGridLine()) {
         plot.setRangeGridlinePaint(plotInfo.getHGridLineColor());
      }
   }
}
