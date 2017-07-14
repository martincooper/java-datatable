import io.vavr.collection.List;
import io.vavr.control.Try;

/**
 * DataTable. The main top level class.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataTable {

    private final String name;
    private final DataColumnCollection columns;

    private DataTable(String tableName) {
        this.name = tableName;
        this.columns = new DataColumnCollection(this);
    }

    public static Try<DataTable> build(String tableName) {
        return Try.success(new DataTable(tableName));
    }

    public static Try<DataTable> build(String tableName, Iterable<IDataColumn> columns) {

        Try result = validateColumns(columns);

        if (result.isFailure())
            return Try.failure(result.getCause());

        return Try.success(new DataTable(tableName));
    }

    private static Try<Void> validateColumns(Iterable<IDataColumn> columns) {
        List<IDataColumn> dataCols = List.ofAll(columns);

        return validateColumnNames(dataCols)
                .andThen(x -> validateColumnDataLength(dataCols));
    }

    private static Try<Void> validateColumnNames(List<IDataColumn> columns) {
        if (columns.groupBy(IDataColumn::getName).length() != columns.length())
            return Try.failure(new DataTableException("Columns contain duplicate names."));

        return Try.success(null);
    }

    private static Try<Void> validateColumnDataLength(List<IDataColumn> columns) {
        if (List.ofAll(columns).groupBy(x -> x.getData().length()).length() > 1)
            return Try.failure(new DataTableException("Columns have uneven row count."));

        return Try.success(null);
    }
}
