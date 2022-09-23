package club.javafamily.lens;

import club.javafamily.common.IndexedSparseMatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:35
 * @description
 */
public abstract class AbstractTableLens implements TableFilter, EditableLens {

   protected int rowCount = DEFAULT_HEADER_COUNT;
   protected int colCount;
   protected int headerRowCount = DEFAULT_HEADER_COUNT;
   protected int headerColCount = DEFAULT_HEADER_COUNT;
   protected TableLens table;
   protected Map<Integer, Class<?>> columnTypes = new HashMap<>();
   protected IndexedSparseMatrix rowSpanMap = new IndexedSparseMatrix();
   protected IndexedSparseMatrix colSpanMap = new IndexedSparseMatrix();

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

   @Override
   public int getHeaderRowCount() {
      return headerRowCount;
   }

   @Override
   public void setHeaderRowCount(int headerRowCount) {
      this.headerRowCount = headerRowCount;
   }

   @Override
   public int getHeaderColCount() {
      return headerColCount;
   }

   public void setHeaderColCount(int headerColCount) {
      this.headerColCount = headerColCount;
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

   @Override
   public int getRowSpan(int row, int col) {
      final Integer span = (Integer) rowSpanMap.get(row, col);

      if(table != null && span == null) {
         return table.getRowSpan(row, col);
      }

      return span == null ? DEFAULT_SPAN : span;
   }

   @Override
   public void setRowSpan(int row, int col, Integer span) {
      rowSpanMap.set(row, col, span);

      for (int i = 1; i < span; i++) {
         rowSpanMap.set(row + i, col, SPAN_CELL);
      }
   }

   @Override
   public int getColSpan(int row, int col) {
      final Integer span = (Integer) colSpanMap.get(row, col);

      if(table != null && span == null) {
         return table.getColSpan(row, col);
      }

      return span == null ? DEFAULT_SPAN : span;
   }

   @Override
   public void setColSpan(int row, int col, Integer span) {
      colSpanMap.set(row, col, span);

      for (int i = 1; i < span; i++) {
         rowSpanMap.set(row, col + i, SPAN_CELL);
      }
   }
}
