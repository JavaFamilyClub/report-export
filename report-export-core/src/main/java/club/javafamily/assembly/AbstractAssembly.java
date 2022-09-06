package club.javafamily.assembly;

import club.javafamily.common.DoublePoint;
import club.javafamily.constants.StyleLayoutConstants;
import club.javafamily.lens.TableLens;
import club.javafamily.style.StyleLayout;

public abstract class AbstractAssembly <SL extends StyleLayout> implements Assembly<SL> {

    private String id;

    private TableLens lens;

    private SL styleLayout;

    private double width = StyleLayoutConstants.ASSEMBLY_WIDTH_AUTO;

    private double height = StyleLayoutConstants.ASSEMBLY_HEIGHT_AUTO;

    private DoublePoint position;

    private String title;

    private boolean titleVisible;

    @Override
    public DoublePoint getPosition() {
        return position;
    }

    @Override
    public SL getStyleLayout() {
        return styleLayout;
    }

    @Override
    public TableLens getTableLens() {
        return lens;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isTitleVisible() {
        return titleVisible;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLens(TableLens lens) {
        this.lens = lens;
    }

    public void setStyleLayout(SL styleLayout) {
        this.styleLayout = styleLayout;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPosition(DoublePoint position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
    }
}
