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

}
