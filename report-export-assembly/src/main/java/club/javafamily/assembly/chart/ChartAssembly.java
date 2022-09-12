package club.javafamily.assembly.chart;

import club.javafamily.assembly.AbstractAssembly;
import club.javafamily.assembly.chart.axis.*;
import club.javafamily.assembly.chart.binding.ChartBindingInfo;
import club.javafamily.assembly.chart.legend.LegendInfo;
import club.javafamily.assembly.chart.plot.PlotInfo;
import club.javafamily.assembly.chart.style.ChartStyleLayout;
import club.javafamily.assembly.chart.tooltip.TooltipInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jack Li
 * @date 2022/9/7 上午10:34
 * @description Chart Assembly
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartAssembly extends AbstractAssembly<ChartStyleLayout> {

   /**
    * chart 类型
    */
   private int chartType;

   /**
    * Axis info
    */
   private AxisInfo axisInfo;

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

   public ChartBindingInfo getChartBinding() {
      return (ChartBindingInfo) super.getBinding();
   }

}
