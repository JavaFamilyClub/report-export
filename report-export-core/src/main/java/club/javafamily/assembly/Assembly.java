package club.javafamily.assembly;

import club.javafamily.assembly.binding.BindingInfo;
import club.javafamily.common.DoublePoint;
import club.javafamily.lens.TableLens;
import club.javafamily.style.StyleLayout;

/**
 * @author Jack Li
 * @date 2022/9/6 上午9:01
 * @description 报表元素
 */
public interface Assembly<SL extends StyleLayout> {

    /**
     * 元素位置
     * @return Point
     */
    DoublePoint getPosition();

    default float getX() {
        return getPosition() != null ? (float) getPosition().getX() : 0F;
    }

    default float getY() {
        return getPosition() != null ? (float) getPosition().getY() : 0F;
    }

    /**
     * 样式及布局
     * @return StyleLayout
     */
    SL getStyleLayout();

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

    /**
     * 标题是否可见
     * @return bool
     */
    boolean isTitleVisible();

    /**
     * 获取 Assembly 宽度
     * @return width
     */
    float getWidth();

    /**
     * 获取 Assembly 高度
     * @return height
     */
    float getHeight();

    /**
     * 获取 binding 信息
     * @return binding
     */
    BindingInfo getBinding();
}
