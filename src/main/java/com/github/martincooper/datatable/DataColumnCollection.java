package com.github.martincooper.datatable;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * DataColumnCollection. Handles a collection of Data Columns.
 * Created by Martin Cooper on 13/07/2017.
 */
public class DataColumnCollection
        implements IModifiableByColumn<DataTable>, Iterable<IDataColumn> {

    private final DataTable table;
    private final Vector<IDataColumn> columns;

    /**
     * DataColumnCollection constructor. Creates an empty Data Column Collection.
     *
     * @param table The table the column collection belongs to.
     */
    public DataColumnCollection(DataTable table) {
        this(table, List.empty());
    }

    /**
     * DataColumnCollection constructor.
     * Creates a Data Column Collection containing the specified columns.
     *
     * @param table The table the column collection belong to.
     * @param columns The collection of data columns.
     */
    public DataColumnCollection(DataTable table, Iterable<IDataColumn> columns) {
        Guard.notNull(table, "table");
        Guard.itemsNotNull(columns, "columns");

        this.table = table;
        this.columns = Vector.ofAll(columns);
    }

    /**
     * Returns an iterator over elements of type IDataColumn.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<IDataColumn> iterator() {
        return this.columns.iterator();
    }

    /**
     * Map implementation for the DataColumnCollection class.
     *
     * @param mapper The map function.
     * @return Returns a sequence of the applied map.
     */
    public <U> Seq<U> map(Function<? super IDataColumn, ? extends U> mapper) {
        return this.columns.map(mapper);
    }

    /**
     * Returns the IDataColumn at the specified index.
     * Performs bounds check, returns results in a Try.
     *
     * @param index The index to return the IDataColumn.
     * @return Returns the IDataColumn.
     */
    public Try<IDataColumn> tryGet(int index) {
        return VectorExtensions.outOfBounds(this.columns, index)
                ? DataTableException.tryError("Column index out of bounds")
                : Try.success(get(index));
    }

    /**
     * Returns the IDataColumn at the specified index.
     *
     * @param index The index to return the IDataColumn.
     * @return Returns the IDataColumn.
     */
    public IDataColumn get(int index) {
        return this.columns.get(index);
    }

    /**
     * Returns the IDataColumn by name.
     * Performs column name check, returns results in a Try.
     *
     * @param columnName The name of the IDataColumn.
     * @return Returns the IDataColumn.
     */
    public Try<IDataColumn> tryGet(String columnName) {
        Integer idx = columnIdxByName(columnName);

        return idx < 0
                ? DataTableException.tryError("Invalid column name.")
                : Try.success(get(idx));
    }

    /**
     * Returns the IDataColumn by name.
     *
     * @param columnName The name of the IDataColumn.
     * @return Returns the IDataColumn.
     */
    public IDataColumn get(String columnName) {
        return this.columns.get(columnIdxByName(columnName));
    }

    /**
     * The number of columns in the collection.
     *
     * @return Returns the size of the columns collection.
     */
    public int count() {
        return this.columns.length();
    }

    /**
     * Adds a IDataColumn to the column collection.
     *
     * @param newColumn The new columns to add.
     * @return Returns a new DataTable with the item added.
     */
    @Override
    public Try<DataTable> add(IDataColumn newColumn) {
        return checkColumnsAndBuild("adding",
                () -> VectorExtensions.addItem(this.columns, newColumn));
    }

    /**
     * Replaces the column at the specified index with the new column.
     *
     * @param index The index of the item to be replaced.
     * @param newColumn The new column.
     * @return Returns a new collection with the column replaced.
     */
    @Override
    public Try<DataTable> replace(Integer index, IDataColumn newColumn) {
        return checkColumnsAndBuild("replacing",
                () -> VectorExtensions.replaceItem(this.columns, index, newColumn));
    }

    /**
     * Inserts a new column at the specified index.
     *
     * @param index The column index to inserted the column at.
     * @param newColumn The new column.
     * @return Returns a new collection with the column inserted.
     */
    @Override
    public Try<DataTable> insert(Integer index, IDataColumn newColumn) {
        return checkColumnsAndBuild("inserting",
                () -> VectorExtensions.insertItem(this.columns, index, newColumn));
    }

    /**
     * Removes the specified column.
     *
     * @param index The index of the column to be removed.
     * @return Returns a new collection with the column removed.
     */
    @Override
    public Try<DataTable> remove(Integer index) {
        return checkColumnsAndBuild("removing",
                () -> VectorExtensions.removeItem(this.columns, index));
    }

    /**
     * Replaces the old column with the new column.
     *
     * @param columnName The column to be replaced.
     * @param newColumn The new column.
     * @return Returns a new collection with the column replaced.
     */
    @Override
    public Try<DataTable> replace(String columnName, IDataColumn newColumn) {
        return actionByColumnName(columnName, colIdx -> replace(colIdx, newColumn));
    }

    /**
     * Inserts a column after an existing column.
     *
     * @param columnName The column to be inserted after.
     * @param newColumn The new column.
     * @return Returns a new collection with the column inserted.
     */
    @Override
    public Try<DataTable> insert(String columnName, IDataColumn newColumn) {
        return actionByColumnName(columnName, colIdx -> insert(colIdx, newColumn));
    }

    /**
     * Removes a named column.
     *
     * @param columnName The name of the column to be removed.
     * @return Returns a new collection with the column removed.
     */
    @Override
    public Try<DataTable> remove(String columnName) {
        return actionByColumnName(columnName, this::remove);
    }

    /**
     * Replaces the old column with the new column.
     *
     * @param oldColumn The column to be replaced.
     * @param newColumn The new column.
     * @return Returns a new collection with the column replaced.
     */
    @Override
    public Try<DataTable> replace(IDataColumn oldColumn, IDataColumn newColumn) {
        return replace(this.columns.indexOf(oldColumn), newColumn);
    }

    /**
     * Inserts a new column after the specified column.
     *
     * @param currentColumn The column to be inserted after.
     * @param newColumn The new column.
     * @return Returns a new collection with the column inserted.
     */
    @Override
    public Try<DataTable> insert(IDataColumn currentColumn, IDataColumn newColumn) {
        return insert(this.columns.indexOf(currentColumn), newColumn);
    }

    /**
     * Removes the specified column.
     *
     * @param columnToRemove The column to be removed.
     * @return Returns a new collection with the column removed.
     */
    @Override
    public Try<DataTable> remove(IDataColumn columnToRemove) {
        return remove(this.columns.indexOf(columnToRemove));
    }

    private Try<DataTable> actionByColumnName(String columnName, Function<Integer, Try<DataTable>> action) {
        Integer idx = columnIdxByName(columnName);

        return idx < 0
                ? error("Column not found with name " + columnName)
                : action.apply(idx);
    }

    private Integer columnIdxByName(String columnName) {
        return this.columns.indexWhere(col -> compare(col.name(), columnName));
    }

    private Try<DataTable> checkColumnsAndBuild(String changeType, Supplier<Try<Vector<IDataColumn>>> columns) {
        // Calculate the new column collection then try and build a DataTable from it.
        Try<DataTable> result = columns.get()
                .flatMap(cols -> DataTable.build("", cols));

        return result.isSuccess()
                ? result
                : error("Error " + changeType + " column at specified index.", result.getCause());
    }

    private static Try<DataTable> error(String errorMessage) {
        return DataTableException.tryError(errorMessage);
    }

    private static Try<DataTable> error(String errorMessage, Throwable exception) {
        return DataTableException.tryError(errorMessage, exception);
    }

    private static boolean compare(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }
}
