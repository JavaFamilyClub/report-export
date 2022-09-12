package club.javafamily.exporter;

public interface AssemblyExporter {

    /**
     * 准备导出
     */
    void prepareExport();

    /**
     * 导出
     */
    void export();

    /**
     * 导出完成
     */
    void completeExport();
}
