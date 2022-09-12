package club.javafamily.assembly.report.style;

import club.javafamily.common.FloatInsets;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReportSheetStyleLayout extends AbstractReportLayout {

    private FloatInsets pageInsets = DEFAULT_INSETS;

    public float getTop() {
        return pageInsets.top;
    }

    public void setTop(float top) {
        pageInsets.top = top;
    }

    public float getLeft() {
        return pageInsets.left;
    }

    public void setLeft(float left) {
        pageInsets.left = left;
    }

    public float getBottom() {
        return pageInsets.bottom;
    }

    public void setBottom(float bottom) {
        pageInsets.bottom = bottom;
    }

    public float getRight() {
        return pageInsets.right;
    }

    public void setRight(float right) {
        pageInsets.right = right;
    }
}
