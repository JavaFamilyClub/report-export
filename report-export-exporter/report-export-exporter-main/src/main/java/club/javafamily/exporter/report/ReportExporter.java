package club.javafamily.exporter.report;

import club.javafamily.assembly.Assembly;
import club.javafamily.exporter.Exporter;
import club.javafamily.exporter.GeneralAssemblyExporter;

public class ReportExporter extends GeneralAssemblyExporter {

    public ReportExporter(Exporter exporter, Assembly<?> assembly) {
        super(exporter, assembly);
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
