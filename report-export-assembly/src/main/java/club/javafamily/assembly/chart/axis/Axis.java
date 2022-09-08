package club.javafamily.assembly.chart.axis;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Li
 * @date 2022/9/7 下午6:09
 * @description
 */
@Data
public class Axis implements Serializable {

   /**
    * axis title
    */
   private String title;

   private boolean visible = true;

}
