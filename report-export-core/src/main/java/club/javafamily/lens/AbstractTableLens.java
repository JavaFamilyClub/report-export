package club.javafamily.lens;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:35
 * @description
 */
public abstract class AbstractTableLens implements TableFilter, EditableLens {

   private Map<Integer, Class<?>> columnTypes = new HashMap<>();

   @Override
   public Class<?> getColumnType(Integer col) {
      return columnTypes.get(col);
   }
}
