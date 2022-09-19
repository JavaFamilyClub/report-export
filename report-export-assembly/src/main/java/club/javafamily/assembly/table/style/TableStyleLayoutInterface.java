package club.javafamily.assembly.table.style;

import club.javafamily.style.StyleLayout;

/**
 * @author Jack Li
 * @date 2022/9/7 上午10:25
 * @description
 */
public interface TableStyleLayoutInterface extends StyleLayout {

    /**
     * 获取 Cell 高度
     * @param row row index
     * @param col col index
     * @return height
     */
    default Float getHeight(int row, int col) {
        return DEFAULT_TABLE_ROW_HEIGHT;
    }

    /**
     * 设置 height
     * @param row row
     * @param col col
     * @param val height
     */
    void setHeight(int row, int col, Float val);

    /**
     * 获取 Cell 高度
     * @param row row index
     * @return height
     */
    default Float getRowHeight(int row) {
        return DEFAULT_TABLE_ROW_HEIGHT;
    }

    /**
     * 设置 row height
     * @param row row
     * @param val height
     */
    void setRowHeight(int row, Float val);
}
