package club.javafamily.constants;

import club.javafamily.common.FloatInsets;

import java.awt.*;
import java.io.Serializable;

/**
 * @author Jack Li
 * @date 2022/9/3 下午4:53
 * @description 样式及布局常量池
 */
public interface StyleLayoutConstants extends Serializable {

    int NONE = -1;

    /**
     * Assembly 自动布局宽度
     */
    int ASSEMBLY_WIDTH_AUTO = -1;

    /**
     * Assembly 自动布局高度
     */
    int ASSEMBLY_HEIGHT_AUTO = -1;

    /**
     * 默认的表格行高
     */
    float DEFAULT_TABLE_ROW_HEIGHT = 20;

    /**
     * 默认文本字体
     */
    Font DEFAULT_TEXT_FONT = new Font("仿宋", Font.PLAIN, 14);

    /**
     * Default header text font.
     */
   Font DEFAULT_HEADER_FONT = new Font("仿宋", Font.BOLD, 14);

    /**
     * 默认背景色
     */
    Color WHITE = Color.WHITE;

    /**
     * 默认背景色
     */
    Color DEFAULT_BG = WHITE;

    /**
     * 默认 Insets
     */
    FloatInsets DEFAULT_INSETS = new FloatInsets(10, 10, 10, 10);
}
