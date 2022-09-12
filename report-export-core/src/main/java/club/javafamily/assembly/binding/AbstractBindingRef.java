package club.javafamily.assembly.binding;

import java.text.Format;

/**
 * @author Jack Li
 * @date 2022/9/9 上午9:20
 * @description
 */
public class AbstractBindingRef implements BindingRef {

   private String columnName;
   private int colIndex;
   private Format format;
   private boolean visible = true;

   @Override
   public String getColumnName() {
      return columnName;
   }

   @Override
   public int getColIndex() {
      return colIndex;
   }

   @Override
   public Format getFormat() {
      return format;
   }

   @Override
   public boolean isVisible() {
      return visible;
   }

   public void setColumnName(String columnName) {
      this.columnName = columnName;
   }

   public void setFormat(Format format) {
      this.format = format;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }
}
