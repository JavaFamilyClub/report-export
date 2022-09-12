package club.javafamily.lens;

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
     * get header row count
     * @return header count
     */
    default int getHeaderRowCount() {
        return 1;
    }

    /**
     * 获取 header 的 col 下标
     * @param header
     * @return
     */
    default int columnIndex(Object header) {
        // TODO
        return 0;
    }
}
