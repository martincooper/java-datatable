package com.github.martincooper.datatable;

import io.vavr.control.Try;

/**
 * DataRowCollectionModifiable. Handles a collection of DataRows
 * Created by Martin Cooper on 17/07/2017.
 */
public class DataRowCollectionModifiable extends DataRowCollectionBase {

    /**
     * Private DataRow constructor.
     * Use 'build' to create instance.
     *
     * @param table The DataTable the DataRow is pointing to.
     * @param rows The DataRows.
     */
    private DataRowCollectionModifiable(DataTable table, Iterable<DataRow> rows) {
        super(table, rows);
    }

    /**
     * Returns a new DataTable with the additional row appended.
     *
     * @param rowValues The values to append to the row.
     * @return Returns a new DataTable with the row appended.
     */
    public Try<DataTable> add(Object[] rowValues) {
        return null;
    }

    /**
     * Returns a new DataTable with the additional row inserted at the specified index.
     *
     * @param idx The row index.
     * @param rowValues The values to insert into the row.
     * @return Returns a new DataTable with the row inserted.
     */
    public Try<DataTable> insert(int idx, Object[] rowValues) {
        return null;
    }

    /**
     * Returns a new DataTable with the data replaced at the specified index.
     *
     * @param idx The row index.
     * @param rowValues The new values to replaced the old ones.
     * @return Returns a new DataTable with the row inserted.
     */
    public Try<DataTable> replace(int idx, Object[] rowValues) {
        return null;
    }

    /**
     * Returns a new DataTable with the specified row removed.
     *
     * @param idx The row index.
     * @return Returns a new DataTable with the row removed.
     */
    public Try<DataTable> remove(int idx) {
        return null;
    }

    /**
     * Builds a new DataRowCollection for the specified DataTable.
     * @param table The table to build the DataRowCollection for.
     * @return Returns the DataRowCollection.
     */
    public static DataRowCollectionModifiable build(DataTable table) {
        return buildRowCollection(table, DataRowCollectionModifiable::new);
    }
}
