package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.DataColumnCollection;
import com.github.martincooper.datatable.DataRow;
import com.github.martincooper.datatable.DataTable;
import com.github.martincooper.datatable.IDataColumn;
import io.vavr.control.Try;

import java.util.function.Function;

/**
 * ColumnIdentity. Maps a column name, or column index to the actual column.
 * Used for sorting & multi-sorting.
 * Created by Martin Cooper on 21/07/2017.
 */
final class ColumnIdentity {

    private final Function<DataColumnCollection, Try<IDataColumn>> getColumn;

    /**
     * ColumnIdentity constructor. Uses column name to identify column.
     *
     * @param columnName The column name.
     */
    ColumnIdentity(String columnName) {
        this.getColumn = cols -> cols.tryGet(columnName);
    }

    /**
     * ColumnIdentity constructor. Uses column name to identify column.
     *
     * @param columnIndex The column index.
     */
    ColumnIdentity(Integer columnIndex) {
        this.getColumn = cols -> cols.tryGet(columnIndex);
    }

    /**
     * Returns the column from the specified table.
     *
     * @param table The table to get the column from.
     * @return Returns the IDataColumn if found, as a Try.
     */
    Try<IDataColumn> getColumn(DataTable table) {
        return getColumn.apply(table.columns());
    }

    /**
     * Returns the row / column value from the specified table.
     *
     * @param dataRow The DataRow to get the table and column from.
     * @return Returns the data if found, as a Try.
     */
    Try<Object> getCellData(DataRow dataRow) {
        return getColumn
                .apply(dataRow.table().columns())
                .flatMap(col -> Try.success(col.valueAt(dataRow.rowIdx())));
    }
}
