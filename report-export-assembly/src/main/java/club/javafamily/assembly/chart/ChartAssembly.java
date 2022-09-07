package club.javafamily.assembly.chart;

import club.javafamily.assembly.AbstractAssembly;
import club.javafamily.assembly.chart.axis.Axis;
import club.javafamily.assembly.chart.series.DomainSeries;
import club.javafamily.assembly.chart.series.Series;
import club.javafamily.assembly.chart.style.ChartStyleLayout;
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

   private List<Axis> xAxis;
   private List<Axis> yAxis;

   private DomainSeries domainSeries;
   private List<Series> valueSeries;

   /**
    * 获取 x1 Axis
    */
   public Axis primaryXAxis() {
      return xAxis.get(0);
   }

   /**
    * 获取 y1 Axis
    */
   public Axis primaryYAxis() {
      return yAxis.get(0);
   }
}
