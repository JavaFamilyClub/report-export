package club.javafamily.assembly.table.style;

import club.javafamily.common.IndexedSparseMatrix;
import club.javafamily.style.AbstractStyleLayout;

/**
 * @author Jack Li
 * @date 2022/9/7 上午10:28
 * @description
 */
public abstract class TableStyleLayout extends AbstractStyleLayout
   implements TableStyleLayoutInterface
{
    protected IndexedSparseMatrix heightMap = new IndexedSparseMatrix();

    public TableStyleLayout getTableStyleLayout() {
        return (TableStyleLayout) getStyleLayout();
    }

    /**
     * 获取 Cell 高度
     *
     * @param row row index
     * @param col col index
     * @return height
     */
    @Override
    public Float getHeight(int row, int col) {
        if(getTableStyleLayout() != null) {
            return getTableStyleLayout().getHeight(row, col);
        }

        Float height = (Float) heightMap.getByLevel(row, col);

        return height == null
                ? TableStyleLayoutInterface.super.getHeight(row, col)
                : null;
    }

    /**
     * 获取 Cell 高度
     *
     * @param row row index
     * @return height
     */
    @Override
    public Float getRowHeight(int row) {
        if(getTableStyleLayout() != null) {
            return getTableStyleLayout().getRowHeight(row);
        }

        Float height = (Float) heightMap.getRow(row);

        return height == null
                ? TableStyleLayoutInterface.super.getRowHeight(row)
                : null;
    }

    /**
     * 设置 height
     *
     * @param row row
     * @param col col
     * @param val height
     */
    @Override
    public void setHeight(int row, int col, Float val) {
        heightMap.set(row, col, val);
    }

    /**
     * 设置 row height
     *
     * @param row row
     * @param val height
     */
    @Override
    public void setRowHeight(int row, Float val) {
        heightMap.setRow(row, val);
    }
}
