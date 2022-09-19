package club.javafamily.style;

import club.javafamily.common.IndexedSparseMatrix;

import java.awt.*;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:00
 * @description 指定 Assembly 的样式及布局
 */
public class AbstractStyleLayout implements StyleLayout {

   private StyleLayout styleLayout;
   protected IndexedSparseMatrix foregroundMap = new IndexedSparseMatrix();
   protected IndexedSparseMatrix backgroundMatrix = new IndexedSparseMatrix();
   protected IndexedSparseMatrix alignMatrix = new IndexedSparseMatrix();
   protected IndexedSparseMatrix insetsMatrix = new IndexedSparseMatrix();
   protected IndexedSparseMatrix fontMatrix = new IndexedSparseMatrix();
   protected IndexedSparseMatrix textColorMatrix = new IndexedSparseMatrix();
   protected Font titleFont;
   protected Color titleFontColor;

   @Override
   public StyleLayout getStyleLayout() {
      return styleLayout;
   }

   @Override
   public Color getBackground(int row, int col) {
      final Color clr = (Color) backgroundMatrix.getByLevel(row, col);

      return (clr == null && styleLayout != null)
         ? styleLayout.getBackground(row, col) : clr;
   }

   @Override
   public Insets getInset(int row, int col) {
      final Insets val = (Insets) insetsMatrix.getByLevel(row, col);

      return (val == null && styleLayout != null)
              ? styleLayout.getInset(row, col) : val;
   }

   /**
    * getting font
    *
    * @param row row
    * @param col col
    * @return font
    */
   @Override
   public Font getFont(int row, int col) {
      Font val = (Font) fontMatrix.getByLevel(row, col);

      return (val == null && styleLayout != null)
              ? styleLayout.getFont(row, col) : val;
   }

   public void setFont(int row, int col, Font val) {
      fontMatrix.set(row, col, val);
   }

   /**
    * get text color
    *
    * @param row row index
    * @param col col index
    * @return Color
    */
   @Override
   public Color getFontColor(int row, int col) {
      Color val = (Color) textColorMatrix.getByLevel(row, col);

      return (val == null && styleLayout != null)
              ? styleLayout.getFontColor(row, col) : val;
   }

   public void setFontColor(int row, int col, Color color) {
      textColorMatrix.set(row, col, color);
   }

   @Override
   public Font getTitleFont() {
      return titleFont;
   }

   @Override
   public Color getTitleFontColor() {
      return titleFontColor;
   }

   public void setBackground(int row, int col, Color color) {
      backgroundMatrix.set(row, col, color);
   }

   public void setRowBackground(int row, Color color) {
      backgroundMatrix.setRow(row, color);
   }

   public void setColBackground(int col, Color color) {
      backgroundMatrix.setColumn(col, color);
   }

}
