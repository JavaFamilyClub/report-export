package club.javafamily.assembly.binding;

import java.text.Format;

/**
 * @author Jack Li
 * @date 2022/9/9 上午9:15
 * @description
 */
public interface BindingRef {

   /**
    * 获取列名称
    * @return name for {@link club.javafamily.lens.TableLens}
    */
   String getColumnName();

   /**
    * 获取列下标
    * @return index for {@link club.javafamily.lens.TableLens}
    */
   int getColIndex();

   /**
    * binding of formatter
    * @return format
    */
   Format getFormat();

   /**
    * col is visible if <code>true</code>
    * @return visible
    */
   boolean isVisible();

}
