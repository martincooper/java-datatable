import io.vavr.collection.List;
import io.vavr.control.Try;

/**
 * DataTable class.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataTable {

    private final String name;
    private final DataColumnCollection columns;
    private final DataRowCollection rows;

    /**
     * Private DataTable constructor. Empty Table with no columns.
     * Use 'build' to create instance.
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
     * @param tableName The name of the table.
     * @param columns The collection of columns in the table.
     */
    private DataTable(String tableName, Iterable<IDataColumn> columns) {
        this.name = tableName;
        this.columns = new DataColumnCollection(this, columns);
        this.rows = DataRowCollection.build(this);
    }

    /**
     *
     * @return Returns the table name.
     */
    public String getName() { return this.name; }

    /**
     *
     * @return Returns the columns collection.
     */
    public DataColumnCollection columns() { return this.columns; }

    /**
     * Returns the rowCount / row count of the table.
     * @return The row count of the table.
     */
    public Integer rowCount() {
        return this.columns.count() > 0
                ? this.columns.get(0).getData().length()
                : 0;
    }

    /**
     * Builds an instance of a DataTable.
     * @param tableName The name of the table.
     * @return Returns an instance of a DataTable.
     */
    public static DataTable build(String tableName) {
        return new DataTable(tableName);
    }

    /**
     * Builds an instance of a DataTable.
     * Columns are validated before creation, returning a Failure on error.
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
     * @param columns The columns to check.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateColumnNames(List<IDataColumn> columns) {
        if (columns.groupBy(IDataColumn::getName).length() != columns.length())
            return Try.failure(new DataTableException("Columns contain duplicate names."));

        return Try.success(null);
    }

    /**
     * Validates the number of items in each column is the same.
     * @param columns The columns to check.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateColumnDataLength(List<IDataColumn> columns) {
        if (List.ofAll(columns).groupBy(x -> x.getData().length()).length() > 1)
            return Try.failure(new DataTableException("Columns have different lengths."));

        return Try.success(null);
    }
}
