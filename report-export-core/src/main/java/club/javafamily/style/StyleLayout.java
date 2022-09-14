package club.javafamily.style;

import club.javafamily.constants.StyleLayoutConstants;

import java.awt.*;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:00
 * @description 指定 Assembly 的样式及布局
 */
public interface StyleLayout extends StyleLayoutConstants {

    /**
     * 获取包装 styleLayout
     * @return styleLayout
     */
    StyleLayout getStyleLayout();

    /**
     * getting background color
     * @param row row index
     * @param col col index
     * @return color
     */
    Color getBackground(int row, int col);

    /**
     * getting insets
     * @param row row
     * @param col col
     * @return Insets
     */
    Insets getInset(int row, int col);

    /**
     * title font
     * @return font
     */
    Font getTitleFont();

    /**
     * title font color
     * @return font
     */
    Color getTitleFontColor();
}
