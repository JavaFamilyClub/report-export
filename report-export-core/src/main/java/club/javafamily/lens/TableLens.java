package club.javafamily.lens;

import club.javafamily.utils.spring.ObjectUtils;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:00
 * @description TableLens 顶层接口
 */
public interface TableLens {

    /**
     * getting obj
     * @param row row index
     * @param col col index
     * @return cell data
     */
    Object getObject(int row, int col);

    /**
     * 获取列类型
     * @param col index
     * @return class
     */
    Class<?> getColumnType(Integer col);

    /**
     * get row count
     * @return count
     */
    int getRowCount();

    /**
     * get col count
     * @return count
     */
    int getColCount();

    /**
     * get header row count
     * @return header count
     */
    default int getHeaderRowCount() {
        return 1;
    }

    /**
     * get header col count
     * @return header count
     */
    default int getHeaderColCount() {
        return 1;
    }

    /**
     * 获取 header 的 col 下标
     * @param header header
     * @return index
     */
    default Integer columnIndex(Object header) {
        if(getRowCount() < 1 || getColCount() < 1 || header == null) {
            return null;
        }

        for(int row = 0; row < getHeaderRowCount(); row++) {
            for(int col = 0; col < getColCount(); col++) {
                Object cell = getObject(row, col);

                if(cell != null && cell.equals(header)) {
                    return col;
                }
            }
        }

        return null;
    }

    default boolean isRowHeader(int row) {
        return row < getHeaderRowCount();
    }

    default boolean isColHeader(int col) {
        return col < getHeaderColCount();
    }

    default boolean isHeader(int row, int col) {
        return isRowHeader(row) || isColHeader(col);
    }
}
