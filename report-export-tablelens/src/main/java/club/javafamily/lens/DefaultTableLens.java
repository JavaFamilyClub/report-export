package club.javafamily.lens;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Li
 * @date 2022/9/17 下午9:35
 * @description TableLens 默认实现
 */
public class DefaultTableLens extends AbstractFormatableTableLens {

    private List<Object[]> values = new ArrayList<>();

    public DefaultTableLens(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        reset();
    }

    public DefaultTableLens(List<Object[]> data) {
        this.values = data;
        this.rowCount = data.size();
        this.colCount = data.size() > 0 ? data.get(0).length : 0;
    }

    public DefaultTableLens(List<Object[]> data,
                            int headerRowCount,
                            int headerColCount)
    {
       this(data);
       this.headerRowCount = headerRowCount;
       this.headerColCount = headerColCount;
    }

    public DefaultTableLens(Object[][] data) {
        for (int i = 0; i < data.length; i++) {
            values.add(data[i]);

            this.colCount = Math.max(data[i].length, this.colCount);
        }

        this.rowCount = data.length;
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
        return values.get(row)[col];
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
        values.get(row)[col] = val;
    }

    @Override
    public void reset() {
        super.reset();

        if (getTable() != null) {
            throw new UnsupportedOperationException("Unsupported");
        }

        if(rowCount <=0 || colCount <= 0) {
            return;
        }

        for (int r = 0; r < rowCount; r++) {
            Object[] cols = new Object[colCount];

            values.add(cols);
        }
    }
}
