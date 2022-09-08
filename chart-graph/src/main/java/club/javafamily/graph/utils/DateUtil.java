package club.javafamily.graph.utils;

import java.time.*;
import java.util.Date;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:45
 * @description Merge to javafamily-utils
 */
public final class DateUtil {

   /**
    * 判断一个类型是否是 Date 类型
    * @param clazz
    * @return
    */
   public static boolean isDatetime(Class<?> clazz) {
      return Date.class.isAssignableFrom(clazz)
         || LocalDateTime.class.isAssignableFrom(clazz)
         || LocalDate.class.isAssignableFrom(clazz)
         || LocalTime.class.isAssignableFrom(clazz)
         ;
   }

}
