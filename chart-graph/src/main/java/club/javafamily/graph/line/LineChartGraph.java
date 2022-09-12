package club.javafamily.graph.line;

import club.javafamily.assembly.chart.ChartAssembly;
import club.javafamily.assembly.chart.impl.xy.axis.XyAxisInfo;
import club.javafamily.assembly.chart.plot.PlotInfo;
import club.javafamily.enums.ChartType;
import club.javafamily.graph.ChartGraph;
import club.javafamily.graph.utils.GraphUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;

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

      final XyAxisInfo axisInfo = (XyAxisInfo) assembly.getAxisInfo();

      JFreeChart chart = ChartFactory.createXYLineChart(assembly.getTitle(),
         axisInfo.primaryX1Axis().getTitle(),
         axisInfo.primaryY1Axis().getTitle(),
         dataset, PlotOrientation.VERTICAL,
         assembly.getLegendInfo().isVisible(),
         assembly.getTooltipInfo().isVisible(),
         false);

      initPlot((XYPlot) chart.getPlot(), assembly.getPlotInfo());
      initAxis(chart);
      initLegend(chart.getLegend());

      return chart.createBufferedImage((int) assembly.getWidth(),
         (int) assembly.getHeight());
   }

   private void initAxis(JFreeChart chart) {
      final XYPlot plot = (XYPlot) chart.getPlot();

      PeriodAxis domainAxis = new PeriodAxis("Date");
//        domainAxis.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
      domainAxis.setAutoRangeTimePeriodClass(Hour.class);
      domainAxis.setMajorTickTimePeriodClass(Hour.class);
      PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[]{
         new PeriodAxisLabelInfo(Hour.class,
            new SimpleDateFormat("HH"),
            new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D),
            new Font("SansSerif", 1, 12), Color.BLUE, true, new BasicStroke(0.0F), Color.LIGHT_GRAY),
         new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("dd日"))};
      domainAxis.setLabelInfo(info);
      plot.setDomainAxis(domainAxis);
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
