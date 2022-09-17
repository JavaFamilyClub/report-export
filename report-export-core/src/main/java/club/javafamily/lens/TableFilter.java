package club.javafamily.lens;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:35
 * @description
 */
public interface TableFilter extends TableLens {

    /**
     * 获取装饰 Table
     * @return TableLens
     */
    TableLens getTable();

    /**
     * 设置装饰 Table
     * @param lens 装饰 Table
     */
    void setTable(TableLens lens);

    /**
     * 获取包装 table 的 row index
     * @param row 外层 table row index
     * @return index
     */
    default int getBaseRowIndex(int row) {
        return row;
    }

    /**
     * 获取包装 table 的 col index
     * @param col 外层 table col index
     * @return index
     */
    default int getBaseColIndex(int col) {
        return col;
    }

}
