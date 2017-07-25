package datatable.sorting;

import datatable.DataColumnCollection;
import datatable.DataTable;
import datatable.IDataColumn;
import io.vavr.control.Try;

import java.util.function.Function;

/**
 * ColumnIdentity. Maps a column name, or column index to the actual column.
 * Used for sorting & multi-sorting.
 * Created by Martin Cooper on 21/07/2017.
 */
public final class ColumnIdentity {

    private final Function<DataColumnCollection, Try<IDataColumn>> getColumn;

    /**
     * ColumnIdentity constructor. Uses column name to identify column.
     *
     * @param columnName The column name.
     */
    public ColumnIdentity(String columnName) {
        this.getColumn = cols -> cols.tryGet(columnName);
    }

    /**
     * ColumnIdentity constructor. Uses column name to identify column.
     *
     * @param columnIndex The column index.
     */
    public ColumnIdentity(Integer columnIndex) {
        this.getColumn = cols -> cols.tryGet(columnIndex);
    }

    /**
     * Returns the column from the specified table.
     *
     * @param table The table to get the column from.
     * @return Returns the IDataColumn if found, as a Try.
     */
    public Try<IDataColumn> getColumn(DataTable table) {
        return getColumn.apply(table.columns());
    }
}
