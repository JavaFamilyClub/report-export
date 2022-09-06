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



}
