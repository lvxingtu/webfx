package naga.core.spi.gui.gwtmaterial.nodes;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import naga.core.ngui.displayresultset.DisplayResultSet;
import naga.core.spi.gui.nodes.Table;
import naga.core.util.Strings;
import naga.core.util.collection.IdentityList;

/**
 * @author Bruno Salmon
 */
public class GwtTable extends GwtDisplayResultSetNode<DataGrid<Integer>> implements Table<DataGrid<Integer>> {

    public GwtTable() {
        this(new DataGrid<>());
    }

    public GwtTable(DataGrid<Integer> node) {
        super(node);
        node.addStyleName("bordered");
    }

    @Override
    protected void onNextDisplayResult(DisplayResultSet displayResultSet) {
        for (int columnIndex = 0; columnIndex < displayResultSet.getColumnCount(); columnIndex++) {
            GwtColumn column;
            if (columnIndex < node.getColumnCount())
                column = (GwtColumn) node.getColumn(columnIndex);
            else
                node.addColumn(column = new GwtColumn(columnIndex), Strings.toString(displayResultSet.getHeaderValues()[columnIndex]));
            column.displayResultSet = displayResultSet;
        }
        int rowCount = displayResultSet.getRowCount();
        if (node.getRowCount() != rowCount) {
            node.setRowCount(0, true);
            node.setRowData(new IdentityList(rowCount));
            node.setRowCount(rowCount, true);
            node.redraw(); // otherwise the change on setRowData() is not considered
        }
    }

    private static class GwtColumn extends TextColumn<Integer> {
        private final int columnIndex;
        private DisplayResultSet displayResultSet;

        public GwtColumn(int columnIndex) {
            this.columnIndex = columnIndex;
        }

        @Override
        public String getValue(Integer rowIndex) {
            return (String) displayResultSet.getValue(rowIndex, columnIndex);
        }
    }
}
