package club.javafamily.lens;

import java.util.ArrayList;
import java.util.List;

public class DefaultTableLens extends AbstractFormatableTableLens {

    private List<Object[]> values = new ArrayList<>();

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
}
