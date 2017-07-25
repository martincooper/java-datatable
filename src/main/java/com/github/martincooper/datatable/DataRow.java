package com.github.martincooper.datatable;

import io.vavr.control.Try;

/**
 * A row of data in the DataTable.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataRow {

    private final DataTable table;
    private final Integer rowIdx;

    /**
     * Private DataRow constructor.
     * Use 'build' to create instance.
     *
     * @param table The DataTable the DataRow is pointing to.
     * @param rowIdx The row index.
     */
    private DataRow(DataTable table, Integer rowIdx) {
        this.table = table;
        this.rowIdx = rowIdx;
    }

    /**
     * Returns the underlying Data Table for this row.
     *
     * @return Returns the Data Table for this row .
     */
    public DataTable table() {
        return this.table;
    }

    /**
     * Gets the row index.
     *
     * @return Returns the row index.
     */
    public Integer rowIdx() {
        return this.rowIdx;
    }

    /**
     * Returns the data as an array for this row.
     *
     * @return Returns the data for this row in an array.
     */
    public Object[] data() {
        return this.table.columns()
                .map(col -> col.data().get(this.rowIdx))
                .toJavaArray();
    }

    /**
     * Returns the data at the specified column index, returning as an object.
     *
     * @param colIndex The column index to return the value of.
     * @return Returns the value of the row at the specified column.
     */
    public Try<Object> get(Integer colIndex) {
        return columnToValue(this.table.columns().tryGet(colIndex));
    }

    /**
     * Returns the data in the specified column name, returning as an object.
     *
     * @param colName The column to return the value of.
     * @return Returns the value of the row at the specified column.
     */
    public Try<Object> get(String colName) {
        return columnToValue(this.table.columns().tryGet(colName));
    }

    /**
     * Returns the value of a particular row column as a specific type.
     * This method makes no bounds checks or type checks, so any failures
     * will result in an exception being thrown.
     *
     * @param type The data type.
     * @param idx The index of the column.
     * @param <T> The value type.
     * @return Returns the value at the specified index.
     */
    public <T> T getAs(Class<T> type, Integer idx) {
        Try<DataColumn<T>> col = this.table.columns().get(idx).asType(type);
        return col.get().data().get(this.rowIdx);
    }

    /**
     * Returns the value of a particular row column as a specific type.
     * This method makes no bounds checks or type checks, so any failures
     * will result in an exception being thrown.
     *
     * @param type The data type.
     * @param colName The name of the column.
     * @param <T> The value type.
     * @return Returns the value at the specified index.
     */
    public <T> T getAs(Class<T> type, String colName) {
        Try<DataColumn<T>> col = this.table.columns().get(colName).asType(type);
        return col.get().data().get(this.rowIdx);
    }

    /**
     * Returns the value of a particular row column as a specific type.
     * This method performs bounds checks and type checks. Any errors
     * will return a Try.failure.
     *
     * @param type The data type.
     * @param idx The index of the column.
     * @param <T> The value type.
     * @return Returns the value at the specified index.
     */
    public <T> Try<T> tryGetAs(Class<T> type, Integer idx) {

        // Get the column as it's typed version.
        Try<DataColumn<T>> col = this.table.columns()
                .tryGet(idx)
                .flatMap(c -> c.asType(type));

        return col.isFailure()
                ? Try.failure(col.getCause())
                : Try.success(col.get().data().get(this.rowIdx));
    }

    /**
     * Returns the value of a particular row column as a specific type.
     * This method performs bounds checks and type checks. Any errors
     * will return a Try.failure.
     *
     * @param type The data type.
     * @param colName The name of the column.
     * @param <T> The value type.
     * @return Returns the value at the specified index.
     */
    public <T> Try<T> tryGetAs(Class<T> type, String colName) {

        // Get the column as it's typed version.
        Try<DataColumn<T>> col = this.table.columns()
                .tryGet(colName)
                .flatMap(c -> c.asType(type));

        return col.isFailure()
                ? Try.failure(col.getCause())
                : Try.success(col.get().data().get(this.rowIdx));
    }

    private Try<Object> columnToValue(Try<IDataColumn> column) {
        return column.isSuccess()
                ? Try.success(column.get().data().get(this.rowIdx))
                : Try.failure(column.getCause());
    }

    /**
     * Builds an instance of a DataRow.
     * Row Index is validated before creation, returning a Failure on error.
     *
     * @param table The DataTable the DataRow is pointing to.
     * @param rowIdx The row index.
     * @return Returns a DataRow wrapped in a Try.
     */
    public static Try<DataRow> build(DataTable table, Integer rowIdx) {
        Guard.notNull(table, "table");

        // Check row index bounds, and return new DataRow if success.
        return VectorExtensions.outOfBounds(table.rowCount(), rowIdx)
                ? DataTableException.tryError("Invalid row index for DataRow.")
                : Try.success(new DataRow(table, rowIdx));
    }
}
