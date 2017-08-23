package com.github.martincooper.datatable;

import io.vavr.control.Try;

/**
 * DataRowCollection. Handles a collection of DataRows
 * Created by Martin Cooper on 17/07/2017.
 */
public class DataRowCollection extends DataRowCollectionBase {

    /**
     * Private DataRow constructor.
     * Use 'build' to create instance.
     *
     * @param table The DataTable the DataRow is pointing to.
     * @param rows The DataRows.
     */
    private DataRowCollection(DataTable table, Iterable<DataRow> rows) {
        super(table, rows);
    }

    /**
     * Builds a new DataRowCollection for the specified DataTable.
     * @param table The table to build the DataRowCollection for.
     * @return Returns the DataRowCollection.
     */
    public static DataRowCollection build(DataTable table) {
        return buildRowCollection(table, DataRowCollection::new);
    }

    /**
     * Builds a new DataRowCollection for the specified DataTable.
     * @param table The table to build the DataRowCollection for.
     * @return Returns the DataRowCollection.
     */
    public static Try<DataRowCollection> build(DataTable table, Iterable<DataRow> rows) {
        return buildRowCollection(table, rows, DataRowCollection::new);
    }
}
