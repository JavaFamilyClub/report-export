package club.javafamily.assembly.chart;

import club.javafamily.assembly.AbstractAssembly;
import club.javafamily.assembly.chart.axis.XAxis;
import club.javafamily.assembly.chart.axis.YAxis;
import club.javafamily.assembly.chart.legend.LegendInfo;
import club.javafamily.assembly.chart.plot.PlotInfo;
import club.javafamily.assembly.chart.series.Series;
import club.javafamily.assembly.chart.style.ChartStyleLayout;
import club.javafamily.assembly.chart.tooltip.TooltipInfo;
import lombok.Data;

import java.util.List;

/**
 * @author Jack Li
 * @date 2022/9/7 上午10:34
 * @description Chart Assembly
 */
@Data
public class ChartAssembly extends AbstractAssembly<ChartStyleLayout> {

   /**
    * chart 类型
    */
   private int chartType;

   private List<XAxis> x1Axis;
   private List<YAxis> y1Axis;
   private List<XAxis> x2Axis;
   private List<YAxis> y2Axis;

   /**
    * 数据绑定
    */
   private List<Series> valueSeries;

   /**
    * plot 配置
    */
   private PlotInfo plotInfo = new PlotInfo();

   /**
    * Legend info
    */
   private LegendInfo legendInfo = new LegendInfo();

   /**
    * Tooltip info
    */
   private TooltipInfo tooltipInfo = new TooltipInfo();

   /**
    * 获取 x1 Axis
    */
   public XAxis primaryX1Axis() {
      return x1Axis.get(0);
   }

   /**
    * 获取 y1 Axis
    */
   public YAxis primaryY1Axis() {
      return y1Axis.get(0);
   }
}
