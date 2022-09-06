package club.javafamily.enums;


/**
 * @author Jack Li
 * @date 2021/7/26 5:33 下午
 * @description
 */
public enum ExportType {
    /**
     * Excel 2007
     */
    Excel("Excel", 0, ".xlsx"),
    /**
     * Excel 2003
     */
    Excel_2003("Excel2003", 1, ".xls"),
    /**
     * PDF
     */
    PDF("PDF", 2, ".pdf"),
    /**
     * CSV
     */
    CSV("CSV", 4, ".csv");

    ExportType(String label, int type, String suffix) {
        this.label = label;
        this.type = type;
        this.suffix = suffix;
    }

    private String label;
    private int type;
    private String suffix;

    public static ExportType parse(int type) {
        switch(type) {
            case 0:
                return Excel;
            case 1:
                return Excel_2003;
            case 2:
                return PDF;

            default:
                return null;
        }
    }

    public String getLabel() {
        return label;
    }

    public int getType() {
        return type;
    }

    public String getSuffix() {
        return suffix;
    }
}
