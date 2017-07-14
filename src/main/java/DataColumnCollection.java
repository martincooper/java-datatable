import io.vavr.collection.List;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.util.stream.Stream;

/**
 * DataColumnCollection. Handles a collection of Data Columns.
 * Created by Martin on 13/07/2017.
 */
public class DataColumnCollection {

    private final DataTable table;
    private final Vector<IDataColumn> columns;

    public DataColumnCollection(DataTable table, Iterable<IDataColumn> columns) {
        Guard.notNull(table, "table");
        Guard.itemsNotNull(columns, "columns");

        this.table = table;
        this.columns = Vector.ofAll(columns);
    }

    private Try validateColumns(Iterable<IDataColumn> columns) {
        List<IDataColumn> dataCols = List.ofAll(columns);

        return validateColumnNames(dataCols)
                .map(x -> validateColumnDataLength(dataCols));
    }

    private Try<Void> validateColumnNames(List<IDataColumn> columns) {
        if (columns.groupBy(IDataColumn::getName).length() != columns.length())
            return Try.failure(new DataTableException("Columns contain duplicate names."));

        return Try.success(null);
    }

    private Try validateColumnDataLength(List<IDataColumn> columns) {
        if (List.ofAll(columns).groupBy(x -> x.getData().length()).length() > 1)
            return Try.failure(new DataTableException("Columns have uneven row count."));

        return Try.success(null);
    }
}
