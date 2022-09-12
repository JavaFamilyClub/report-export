package club.javafamily.assembly.chart.impl.xy.binding;

import club.javafamily.assembly.chart.binding.ChartBindingInfo;
import club.javafamily.assembly.chart.impl.xy.ref.XyChartBindingRef;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Li
 * @date 2022/9/9 上午9:12
 * @description
 */
@Data
public class XyChartBindingInfo implements ChartBindingInfo {

   private List<XyChartBindingRef> xBindingRefs = new ArrayList<>();
   private List<XyChartBindingRef> yBindingRefs = new ArrayList<>();

   /**
    * 获取 x1 binding ref
    */
   public XyChartBindingRef primaryX1BindingRef() {
      return xBindingRefs.get(0);
   }

}
