package club.javafamily.assembly.chart.impl.xy.axis;

import club.javafamily.assembly.chart.axis.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Jack Li
 * @date 2022/9/9 上午11:16
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XyAxisInfo extends AxisInfo {
   private List<XAxis> x1Axis;
   private List<YAxis> y1Axis;
   private List<XAxis> x2Axis;
   private List<YAxis> y2Axis;

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
