package club.javafamily.assembly.chart.axis;

import club.javafamily.enums.DateLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.Format;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:16
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XAxis extends Axis {

   /**
    * axis data index in {@link club.javafamily.lens.TableLens}
    */
   private int dataIndex;

   private DateLevel dateLevel;

   private Format format;
}
