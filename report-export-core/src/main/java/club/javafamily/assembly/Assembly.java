package club.javafamily.assembly;

import club.javafamily.lens.TableLens;
import club.javafamily.style.StyleLayout;

import java.awt.*;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:01
 * @description 报表元素
 */
public interface Assembly {

    /**
     * 元素位置
     * @return Point
     */
    Point getPosition();

    /**
     * 样式及布局
     * @return StyleLayout
     */
    StyleLayout getStyleLayout();

    /**
     * 数据
     * @return TableLens
     */
    TableLens getTableLens();

    /**
     * 要素 id
     * @return id
     */
    String getId();

    /**
     * 标题
     * @return 标题
     */
    String getTitle();

}
