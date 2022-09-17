package club.javafamily.lens;

/**
 * @author Jack Li
 * @date 2022/9/8 下午2:35
 * @description
 */
public interface EditableLens {

    /**
     * 修改一个 Cell
     * @param row row index
     * @param col col index
     * @param val cell value
     */
    void setObject(int row, int col, Object val);

    /**
     * Reset
     */
    default void reset() {
        // no op
    }

}
