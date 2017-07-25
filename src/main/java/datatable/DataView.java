package datatable;

import io.vavr.collection.Seq;
import io.vavr.control.Try;

import java.util.Iterator;

/**
 * DataView. Provides a view over a DataTable to store filtered data sets.
 * Created by Martin Cooper on 19/07/2017.
 */
public class DataView implements IBaseTable {

    private final DataTable table;
    private final DataRowCollection rows;

    /**
     * Private DataView constructor. Use build() to construct.
     *
     * @param table The underlying Data Table.
     * @param rows The collection of rows in the view.
     */
    private DataView(DataTable table, DataRowCollection rows) {
        this.table = table;
        this.rows = rows;
    }

    /**
     * Returns an iterator over elements of type DataRow.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<DataRow> iterator() {
        return this.rows.iterator();
    }

    /**
     * @return Returns the table name.
     */
    @Override
    public String name() {
        return this.table.name();
    }

    /**
     * @return Returns the columns collection.
     */
    @Override
    public DataColumnCollection columns() {
        return table.columns();
    }

    /**
     * @return Returns the row collection.
     */
    @Override
    public DataRowCollection rows() {
        return this.rows;
    }

    /**
     * Accessor to a specific row by index.
     *
     * @return Returns a single row.
     */
    @Override
    public DataRow row(Integer rowIdx) {
        return this.rows.get(rowIdx);
    }

    /**
     * @return Returns the underlying table.
     */
    @Override
    public DataTable table() {
        return this.table;
    }

    /**
     * Returns the rowCount / row count of the table.
     *
     * @return The row count of the table.
     */
    @Override
    public Integer rowCount() {
        return this.rows().rowCount();
    }

    /**
     * Return a new DataTable based on this table (clone).
     *
     * @return Returns a new DataTable based on the columns and data in this view.
     */
    @Override
    public DataTable toDataTable() {
        // Get the list of row indexes used in this data view.
        Seq<Integer> rowIndexes = this.rows.map(DataRow::rowIdx);

        // Build a set of new columns with just the data at the specified indexes.
        Seq<IDataColumn> newCols = this.table.columns()
                .map(col -> col.buildFromRows(rowIndexes).get());

        return DataTable.build(this.name(), newCols).get();
    }

    /**
     * Return a new Data View based on this table
     *
     * @return A new Data View based on this table.
     */
    @Override
    public DataView toDataView() {
        return DataView.build(this.table, this.rows).get();
    }

    /**
     * Builds an instance of a DataView.
     * DataRows are validated before creation, returning a Failure on error.
     *
     * @param table The underlying table.
     * @param rows The Data Rows.
     * @return Returns a DataView wrapped in a Try.
     */
    public static Try<DataView> build(DataTable table, Iterable<DataRow> rows) {
        Try<DataRowCollection> result = DataRowCollection.build(table, rows);

        return result.isFailure()
                ? Try.failure(result.getCause())
                : Try.success(new DataView(table, result.get()));
    }
}
