package club.javafamily.lens;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:35
 * @description
 */
public abstract class AbstractTableLens implements TableFilter, EditableLens {

   protected int rowCount;
   protected int colCount;
   protected TableLens table;
   protected Map<Integer, Class<?>> columnTypes = new HashMap<>();

   @Override
   public Class<?> getColumnType(Integer col) {
      return columnTypes.get(col);
   }

   /**
    * 获取装饰 Table
    *
    * @return TableLens
    */
   @Override
   public TableLens getTable() {
      return table;
   }

   /**
    * 设置装饰 Table
    *
    * @param lens 装饰 Table
    */
   @Override
   public void setTable(TableLens lens) {
      this.table = lens;
   }

   /**
    * get row count
    *
    * @return count
    */
   @Override
   public int getRowCount() {
      return rowCount;
   }

   /**
    * get col count
    *
    * @return count
    */
   @Override
   public int getColCount() {
      return colCount;
   }

}
