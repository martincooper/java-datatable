import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * DataTable class.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataTable implements IBaseTable {

    private final String name;
    private final DataRowCollection rows;
    private final DataColumnCollection columns;

    /**
     * Private DataTable constructor. Empty Table with no columns.
     * Use 'build' to create instance.
     *
     * @param tableName The name of the table.
     */
    private DataTable(String tableName) {
        this.name = tableName;
        this.columns = new DataColumnCollection(this);
        this.rows = DataRowCollection.build(this);
    }

    /**
     * Private DataTable Constructor.
     * Use 'build' to create instance.
     *
     * @param tableName The name of the table.
     * @param columns The collection of columns in the table.
     */
    private DataTable(String tableName, Iterable<IDataColumn> columns) {
        this.name = tableName;
        this.columns = new DataColumnCollection(this, columns);
        this.rows = DataRowCollection.build(this);
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
     * The name of the table.
     *
     * @return Returns the table name.
     */
    @Override
    public String name() { return this.name; }

    /**
     * The column collection.
     *
     * @return Returns the columns collection.
     */
    @Override
    public DataColumnCollection columns() { return this.columns; }

    /**
     * The row collection.
     *
     * @return Returns the row collection.
     */
    @Override
    public DataRowCollection rows() { return this.rows; }

    /**
     * The data table.
     *
     * @return Returns the table (this).
     */
    @Override
    public DataTable table() { return this; }

    /**
     * Returns the rowCount / row count of the table.
     *
     * @return The row count of the table.
     */
    @Override
    public Integer rowCount() {
        return this.columns.count() > 0
                ? this.columns.get(0).data().length()
                : 0;
    }

    /**
     * Return a new DataTable based on this table (clone).
     *
     * @return Returns a clone of this DataTable.
     */
    @Override
    public DataTable toDataTable() {
        return DataTable
                .build(this.name, this.columns.toStream())
                .get();
    }

    /**
     * Return a new Data View based on this table.
     *
     * @return A new Data View based on this table.
     */
    @Override
    public DataView toDataView() {
        return DataView
                .build(this, Stream.ofAll(this.rows))
                .get();
    }

    /**
     * Filters the row data using the specified predicate,
     * returning the results as a DataView over the original table.
     *
     * @param predicate The filter criteria.
     * @return Returns a DataView with the filter results.
     */
    public DataView filter(Predicate<DataRow> predicate) {
        return this.rows.filter(predicate);
    }

    /**
     * Builds an instance of a DataTable.
     *
     * @param tableName The name of the table.
     * @return Returns an instance of a DataTable.
     */
    public static DataTable build(String tableName) {
        return new DataTable(tableName);
    }

    /**
     * Builds an instance of a DataTable.
     * Columns are validated before creation, returning a Failure on error.
     *
     * @param tableName The name of the table.
     * @param columns The column collection.
     * @return Returns a DataTable wrapped in a Try.
     */
    public static Try<DataTable> build(String tableName, IDataColumn[] columns) {
        return build(tableName, List.of(columns));
    }

    /**
     * Builds an instance of a DataTable.
     * Columns are validated before creation, returning a Failure on error.
     *
     * @param tableName The name of the table.
     * @param columns The column collection.
     * @return Returns a DataTable wrapped in a Try.
     */
    public static Try<DataTable> build(String tableName, Iterable<IDataColumn> columns) {
        Try result = validateColumns(columns);

        if (result.isFailure())
            return Try.failure(result.getCause());

        return Try.success(new DataTable(tableName, columns));
    }

    /**
     * Validates the column data.
     *
     * @param columns The columns to validate.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateColumns(Iterable<IDataColumn> columns) {
        List<IDataColumn> dataCols = List.ofAll(columns);

        return validateColumnNames(dataCols)
                .flatMap(x -> validateColumnDataLength(dataCols));
    }

    /**
     * Validates there are no duplicate columns names.
     *
     * @param columns The columns to check.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateColumnNames(List<IDataColumn> columns) {
        if (columns.groupBy(IDataColumn::name).length() != columns.length())
            return Try.failure(new DataTableException("Columns contain duplicate names."));

        return Try.success(null);
    }

    /**
     * Validates the number of items in each column is the same.
     *
     * @param columns The columns to check.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateColumnDataLength(List<IDataColumn> columns) {
        if (List.ofAll(columns).groupBy(x -> x.data().length()).length() > 1)
            return Try.failure(new DataTableException("Columns have different lengths."));

        return Try.success(null);
    }
}
