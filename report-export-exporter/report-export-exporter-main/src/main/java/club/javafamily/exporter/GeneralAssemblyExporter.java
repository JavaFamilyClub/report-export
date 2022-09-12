package club.javafamily.exporter;

import club.javafamily.assembly.Assembly;

public class GeneralAssemblyExporter implements AssemblyExporter {

    protected Exporter exporter;
    protected Assembly<?> assembly;

    public GeneralAssemblyExporter(Exporter exporter, Assembly<?> assembly) {
        this.assembly = assembly;
        this.exporter = exporter;
    }

    /**
     * 准备导出
     */
    @Override
    public void prepareExport() {

    }

    /**
     * 导出
     */
    @Override
    public void export() {

    }

    /**
     * 导出完成
     */
    @Override
    public void completeExport() {

    }
}
