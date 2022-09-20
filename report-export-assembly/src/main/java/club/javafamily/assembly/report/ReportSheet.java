package club.javafamily.assembly.report;

import club.javafamily.assembly.AbstractAssembly;
import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.report.style.ReportSheetStyleLayout;
import club.javafamily.common.PageSize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ReportSheet extends AbstractAssembly<ReportSheetStyleLayout> {

    private List<Assembly<?>> assemblies = new ArrayList<>();

    private PageSize pageSize = PageSize.A4;

    /**
     * 获取 assemblies
     * @return list
     */
    public List<Assembly<?>> getAssemblies() {
        return new CopyOnWriteArrayList<>(assemblies);
    }

    /**
     * 新增 assembly
     * @param assembly assembly
     */
    public void addAssembly(Assembly<?> assembly) {
        assemblies.add(assembly);
    }

    @Override
    public float getWidth() {
        return pageSize.getWidth();
    }

    @Override
    public float getHeight() {
        return pageSize.getHeight();
    }

    /**
     * getting page size
     * @return size
     */
    public PageSize getPageSize() {
        return pageSize;
    }

    /**
     * setting page size
     * @return size
     */
    public void setPageSize(PageSize size) {
        this.pageSize = size;
    }

    public void setWidth(float width) {
        this.pageSize.setWidth(width);
    }

    public void setHeight(float height) {
        this.pageSize.setHeight(height);
    }
}
