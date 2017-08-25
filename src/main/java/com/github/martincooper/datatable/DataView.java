package com.github.martincooper.datatable;

import com.github.martincooper.datatable.sorting.DataSort;
import com.github.martincooper.datatable.sorting.SortItem;
import com.github.martincooper.datatable.sorting.SortOrder;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;

import static com.github.martincooper.datatable.TransformCollector.transform;

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
     * Accessor to a specific column by index.
     *
     * @param colIdx The index of the column to return.
     * @return Returns a single column.
     */
    public IDataColumn column(Integer colIdx) {
        return this.table.column(colIdx);
    }

    /**
     * Accessor to a specific column by name.
     *
     * @param colName The name of the column to return.
     * @return Returns a single column.
     */
    public IDataColumn column(String colName) {
        return this.table.column(colName);
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
        return this.table.columns()
                .map(col -> col.buildFromRows(rowIndexes).get())
                .collect(transform(cols -> DataTable.build(this.name(), cols).get()));
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
     * Map operation across the Data Rows in the table.
     *
     * @param mapper The mapper function.
     * @param <U> The return type.
     * @return Returns the mapped results.
     */
    public <U> Seq<U> map(Function<? super DataRow, ? extends U> mapper) {
        return this.rows.map(mapper);
    }

    /**
     * FlatMap implementation for the DataRowCollection class.
     *
     * @param <U> Mapped return type.
     * @param mapper The map function.
     * @return Returns a sequence of the applied flatMap.
     */
    public <U> Seq<U> flatMap(Function<? super DataRow, ? extends Iterable <? extends U>> mapper) {
        return this.rows.flatMap(mapper);
    }

    /**
     * Reduce implementation for the DataRowCollection class.
     *
     * @param reducer The reduce function.
     * @return Returns a single, reduced DataRow.
     */
    public DataRow reduce(BiFunction<? super DataRow, ? super DataRow, ? extends DataRow> reducer) {
        return this.rows.reduce(reducer);
    }

    /**
     * GroupBy implementation for the DataRowCollection class.
     *
     * @param grouper The group by function.
     * @return Returns a map containing the grouped data.
     */
    public <C> Map<C, Vector<DataRow>> groupBy(Function<? super DataRow, ? extends C> grouper) {
        return this.rows.groupBy(grouper);
    }

    /**
     * Fold Left implementation for the DataRowCollection class.
     *
     * @param <U> Fold return type.
     * @param folder The fold function.
     * @return Returns a single value of U.
     */
    public <U> U foldLeft(U zero, BiFunction<? super U, ? super DataRow, ? extends U> folder) {
        return this.rows.foldLeft(zero, folder);
    }

    /**
     * Fold Right implementation for the DataRowCollection class.
     *
     * @param <U> Fold return type.
     * @param folder The fold function.
     * @return Returns a single value of U.
     */
    public <U> U foldRight(U zero, BiFunction<? super DataRow, ? super U, ? extends U> folder) {
        return this.rows.foldRight(zero, folder);
    }

    /**
     * Table QuickSort by single column name.
     *
     * @param columnName The column name to sort.
     * @return Returns the results as a sorted Data View.
     */
    @Override
    public Try<DataView> quickSort(String columnName) {
        return this.quickSort(columnName, SortOrder.Ascending);
    }

    /**
     * Table QuickSort by single column name and a sort order.
     *
     * @param columnName The column name to sort.
     * @param sortOrder  The sort order.
     * @return Returns the results as a sorted Data View.
     */
    @Override
    public Try<DataView> quickSort(String columnName, SortOrder sortOrder) {
        SortItem sortItem = new SortItem(columnName, sortOrder);
        return DataSort.quickSort(this.table, this.rows.asSeq(), Stream.of(sortItem));
    }

    /**
     * Table QuickSort by single column index.
     *
     * @param columnIndex The column index to sort.
     * @return Returns the results as a sorted Data View.
     */
    @Override
    public Try<DataView> quickSort(Integer columnIndex) {
        return quickSort(columnIndex, SortOrder.Ascending);
    }

    /**
     * Table QuickSort by single column index and a sort order.
     *
     * @param columnIndex The column index to sort.
     * @param sortOrder   The sort order.
     * @return Returns the results as a sorted Data View.
     */
    @Override
    public Try<DataView> quickSort(Integer columnIndex, SortOrder sortOrder) {
        SortItem sortItem = new SortItem(columnIndex, sortOrder);
        return DataSort.quickSort(this.table, this.rows.asSeq(), Stream.of(sortItem));
    }

    /**
     * Table QuickSort by single sort item.
     *
     * @param sortItem The sort item.
     * @return Returns the results as a sorted Data View.
     */
    @Override
    public Try<DataView> quickSort(SortItem sortItem) {
        return this.quickSort(Stream.of(sortItem));
    }

    /**
     * Table QuickSort by multiple sort items.
     *
     * @param sortItems The sort items.
     * @return Returns the results as a sorted Data View.
     */
    @Override
    public Try<DataView> quickSort(Iterable<SortItem> sortItems) {
        return DataSort.quickSort(this.table, this.rows.asSeq(), Stream.ofAll(sortItems));
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
        return Match(DataRowCollection.build(table, rows)).of(
                Case($Success($()), dataRows -> Try.success(new DataView(table, dataRows))),
                Case($Failure($()), Try::failure)
        );
    }
}
