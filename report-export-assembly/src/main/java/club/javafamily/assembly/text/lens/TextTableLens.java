package club.javafamily.assembly.text.lens;

import club.javafamily.lens.AbstractFormatableTableLens;
import club.javafamily.utils.Tool;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TextTableLens extends AbstractFormatableTableLens {

    private String text;

    public TextTableLens() {
    }

    public TextTableLens(String text) {
        this.text = text;
    }

    /**
     * 修改一个 Cell
     *
     * @param row row index
     * @param col col index
     * @param val cell value
     */
    @Override
    public void setObject(int row, int col, Object val) {
        this.text = convertToString(val);
    }

    private String convertToString(Object val) {
        return Tool.toString(val);
    }

    /**
     * getting obj
     *
     * @param row row index
     * @param col col index
     * @return cell data
     */
    @Override
    public Object getObject(int row, int col) {
        return text;
    }
}
