package club.javafamily.exporter;

import club.javafamily.assembly.Assembly;
import club.javafamily.assembly.report.ReportSheet;

import java.io.OutputStream;
import java.util.List;

public abstract class AbstractExporter implements Exporter {

    protected ReportSheet reportSheet;
    protected OutputStream out;

    /**
     * 准备导出
     * @param assembly assembly
     * @param out stream
     */
    @Override
    public void prepareExport(Assembly<?> assembly, OutputStream out) {
        if(assembly instanceof ReportSheet) {
            this.reportSheet = (ReportSheet) assembly;
        }

        this.out = out;
    }

    /**
     * 导出报表
     *
     * @param reportSheet ReportSheet
     */
    @Override
    public void exportReport(ReportSheet reportSheet) throws Exception {
        List<Assembly<?>> assemblies = reportSheet.getAssemblies();

        for (Assembly<?> assembly : assemblies) {
            this.export(assembly);
        }
    }

    /**
     * 导出完成
     */
    @Override
    public void completeExport() throws Exception {
        out.flush();
    }

}
