package club.javafamily.lens;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:00
 * @description TableLens 顶层接口
 */
public interface TableLens {

    /**
     * 默认的 span size
     */
    int DEFAULT_SPAN = 1;

    /**
     * 默认的 header count
     */
    int DEFAULT_HEADER_COUNT = 1;

    /**
     * mark cell is span cell
     */
    int SPAN_CELL = -65535;

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
    int getHeaderRowCount();

    /**
     * set header row count
     * @param rowCount row count
     */
    void setHeaderRowCount(int rowCount);

    /**
     * get header col count
     * @return header count
     */
    int getHeaderColCount();

    /**
     * check cell is span cell
     * @param row row index
     * @param col col index
     * @return <code>true</code> mean it is span cell
     */
    default boolean isSpanCell(int row, int col) {
        return getRowSpan(row, col) == SPAN_CELL
           || getColSpan(row, col) == SPAN_CELL;
    }

    /**
     * row span
     * @param row row index
     * @param col col index
     * @return span
     */
    int getRowSpan(int row, int col);

    /**
     * setting row span
     * @param row row index
     * @param col col index
     * @param span span size
     */
    void setRowSpan(int row, int col, Integer span);

    /**
     * col span
     * @param row row index
     * @param col col index
     * @return span
     */
    int getColSpan(int row, int col);

    /**
     * setting col span
     * @param row row index
     * @param col col index
     * @param span span size
     */
    void setColSpan(int row, int col, Integer span);

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
