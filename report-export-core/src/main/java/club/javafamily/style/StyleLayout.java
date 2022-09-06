package club.javafamily.style;

import java.awt.*;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:00
 * @description 指定 Assembly 的样式及布局
 */
public interface StyleLayout {

    /**
     * getting background color
     * @param row row index
     * @param col col index
     * @return color
     */
    Color getBackground(int row, int col);

}
