package datatable;

import io.vavr.collection.List;
import io.vavr.control.Try;

/**
 * DataTableBuilder. Used as a helper to create Data Tables.
 * Created by Martin Cooper on 18/07/2017.
 */
public class DataTableBuilder {

    private final String tableName;
    private List<IDataColumn> dataColumns;

    /**
     * DataTableBuilder. Private constructor. Used by the create() method.
     *
     * @param tableName The name of the table to create.
     */
    private DataTableBuilder(String tableName) {
        this.tableName = tableName;
        this.dataColumns = List.empty();
    }

    /**
     * Creates a new instance of a DataTableBuilder, passing table name.
     *
     * @param tableName The name of the table.
     * @return Returns a new instance of a DataTableBuilder, allowing method chaining.
     */
    public static DataTableBuilder create(String tableName) {
        return new DataTableBuilder(tableName);
    }

    /**
     * Allows an additional column to be added when building a table.
     *
     * @param type The data type of the column.
     * @param columnName The column name.
     * @param data The data contained in the column.
     * @param <T> The column type.
     * @return Adds a new column and returns an instance to the current Data Table Builder.
     */
    public <T> DataTableBuilder withColumn(Class<T> type, String columnName, Iterable<T> data) {
        this.dataColumns = this.dataColumns.append(new DataColumn<T>(type, columnName, data));
        return this;
    }

    /**
     * Allows an additional column to be added when building a table.
     *
     * @param type The data type of the column.
     * @param columnName The column name.
     * @param data The data contained in the column.
     * @param <T> The column type.
     * @return Adds a new column and returns an instance to the current Data Table Builder.
     */
    @SafeVarargs
    public final <T> DataTableBuilder withColumn(Class<T> type, String columnName, T... data) {
        this.dataColumns = this.dataColumns.append(new DataColumn<T>(type, columnName, data));
        return this;
    }

    /**
     * Attempts to build the data table from all the details in the chained method calls.
     *
     * @return Returns a Try DataTable.
     */
    public Try<DataTable> build() {
        return DataTable.build(this.tableName, this.dataColumns);
    }
}
