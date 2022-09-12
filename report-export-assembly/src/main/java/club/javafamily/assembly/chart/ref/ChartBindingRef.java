package club.javafamily.assembly.chart.ref;

import club.javafamily.assembly.binding.AbstractBindingRef;
import club.javafamily.enums.DataTypeSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jack Li
 * @date 2022/9/9 上午10:29
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChartBindingRef extends AbstractBindingRef {

   /**
    * 数据类型
    */
   protected DataTypeSchema type;

}
