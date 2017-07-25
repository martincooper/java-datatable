package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.DataTable;
import com.github.martincooper.datatable.Guard;
import com.github.martincooper.datatable.IDataColumn;
import io.vavr.control.Try;

/**
 * SortItem class. Used to perform table multi sorts.
 * Created by Martin Cooper on 21/07/2017.
 */
public final class SortItem {

    private final ColumnIdentity columnIdentity;
    private final SortOrder sortOrder;

    /**
     * SortItem constructor. Identify by column name.
     *
     * @param columnName The column name.
     */
    public SortItem(String columnName) {
        this(new ColumnIdentity(columnName), SortOrder.Ascending);
    }

    /**
     * SortItem constructor. Identify by column name.
     *
     * @param columnName The column name.
     * @param sortOrder The sort order.
     */
    public SortItem(String columnName, SortOrder sortOrder) {
        this(new ColumnIdentity(columnName), sortOrder);
    }

    /**
     * SortItem constructor. Identify by column index.
     *
     * @param columnIndex The column index.
     */
    public SortItem(Integer columnIndex) {
        this(new ColumnIdentity(columnIndex), SortOrder.Ascending);
    }

    /**
     * SortItem constructor. Identify by column index.
     *
     * @param columnIndex The column index.
     * @param sortOrder The sort order.
     */
    public SortItem(Integer columnIndex, SortOrder sortOrder) {
        this(new ColumnIdentity(columnIndex), sortOrder);
    }

    private SortItem(ColumnIdentity columnIdentity, SortOrder sortOrder) {
        Guard.notNull(columnIdentity, "columnIdentity");
        Guard.notNull(sortOrder, "sortOrder");

        this.columnIdentity = columnIdentity;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the sort order.
     *
     * @return Returns the sort order.
     */
    public SortOrder sortOrder() {
        return this.sortOrder;
    }

    /**
     * Returns the column identity.
     *
     * @return Returns the column identity.
     */
    public ColumnIdentity columnIdentity() {
        return this.columnIdentity;
    }

    /**
     * Returns the column from the table.
     *
     * @param table The table to get the column from.
     * @return Returns the column.
     */
    public Try<IDataColumn> getColumn(DataTable table) {
        return this.columnIdentity.getColumn(table);
    }
}
