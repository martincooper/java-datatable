import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
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
     * @return Returns the underlying table.
     */
    @Override
    public DataTable table() {
        return this.table;
    }

    /**
     * Returns the rowCount / row count of the table.
     * @return The row count of the table.
     */
    @Override
    public Integer rowCount() {
        return this.rows().rowCount();
    }

    /**
     * Return a new DataTable based on this table (clone).
     *
     * @return Returns a clone of this DataTable.
     */
    @Override
    public DataTable toDataTable() {
        // TODO Clean-up.
        Integer[] rowIndexes = this.rows.map(DataRow::rowIdx).toJavaArray(Integer.class);
        IDataColumn[] newCols = this.table.columns()
                .map(col -> col.buildFromRows(rowIndexes).get())
                .toJavaArray(IDataColumn.class);

        return DataTable.build(this.name(), newCols).get();
    }

    /**
     * Return a new Data View based on this table
     * @return A new Data View based on this table.
     */
    @Override
    public DataView toDataView() {
        return DataView
                .build(this.table, Stream.ofAll(this.rows))
                .get();
    }

    /**
     * Builds an instance of a DataView.
     * DataRows are validated before creation, returning a Failure on error.
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
