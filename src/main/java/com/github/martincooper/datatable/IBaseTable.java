package com.github.martincooper.datatable;

import com.github.martincooper.datatable.sorting.IQuickSort;

/**
 * IBaseTable. Specifying common functionality between DataTable and DataView.
 * Created by Martin Cooper on 19/07/2017.
 */
public interface IBaseTable extends Iterable<DataRow>, IQuickSort {

    /**
     * @return Returns the table name.
     */
    String name();

    /**
     * @return Returns the columns collection.
     */
    DataColumnCollection columns();

    /**
     * @return Returns the row collection.
     */
    DataRowCollectionBase rows();

    /**
     * Accessor to a specific row by index.
     *
     * @return Returns a single row.
     */
    DataRow row(Integer rowIdx);

    /**
     * @return Returns the underlying table.
     */
    DataTable table();

    /**
     * Returns the rowCount / row count of the table.
     *
     * @return The row count of the table.
     */
    Integer rowCount();

    /**
     * Return a new DataTable based on this table (clone).
     *
     * @return Returns a clone of this DataTable.
     */
    DataTable toDataTable();

    /**
     * Return a new Data View based on this table.
     *
     * @return A new Data View based on this table.
     */
    DataView toDataView();
}
