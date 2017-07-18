import io.vavr.collection.List;
import io.vavr.control.Try;

/**
 * DataTableBuilder. Used as a helper to create Data Tables.
 * Created by Martin Cooper on 18/07/2017.
 */
public class DataTableBuilder {

    private final String tableName;
    private final List<IDataColumn> dataColumns;

    public DataTableBuilder(String tableName) {
        this.tableName = tableName;
        this.dataColumns = List.empty();
    }

    public static DataTableBuilder build(String tableName) {
        return new DataTableBuilder(tableName);
    }

    public <T> DataTableBuilder withColumn(Class<T> type, String columnName, Iterable<T> data) {
        this.dataColumns.append(new DataColumn<T>(type, columnName, data));
        return this;
    }

    public Try<DataTable> create() {
        return DataTable.build(this.tableName, this.dataColumns);
    }
}
