package com.github.martincooper.datatable;

import io.vavr.collection.*;
import io.vavr.control.Try;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.martincooper.datatable.TransformCollector.transform;

/**
 * DataRowCollectionBase. Handles a collection of DataRows
 * Created by Martin Cooper on 17/07/2017.
 */
abstract class DataRowCollectionBase implements Iterable<DataRow> {

    protected final DataTable table;
    protected final Vector<DataRow> rows;

    /**
     * Protected DataRow constructor.
     * Use 'build' to create instance.
     *
     * @param table The DataTable the DataRow is pointing to.
     * @param rows The DataRows.
     */
    DataRowCollectionBase(DataTable table, Iterable<DataRow> rows) {
        this.table = table;
        this.rows = Vector.ofAll(rows);
    }

    /**
     * Returns an iterator over elements of DataRow.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<DataRow> iterator() {
        return rows.iterator();
    }

    /**
     * Returns the Data Row at the specified index.
     *
     * @param rowIndex The row index.
     * @return Returns the Data Row.
     */
    public DataRow get(Integer rowIndex) {
        return this.rows.get(rowIndex);
    }

    /**
     * The number of rows in the collection.
     *
     * @return Returns the number of rows.
     */
    public Integer rowCount() {
        return this.rows.length();
    }

    /**
     * Returns access to the Data Row collection as a sequence of Data Rows.
     *
     * @return Returns the rows.
     */
    public Seq<DataRow> asSeq() {
        return this.rows;
    }

    /**
     * Filters the row data using the specified predicate,
     * returning the results as a DataView over the original table.
     * @param predicate The filter criteria.
     * @return Returns a DataView with the filter results.
     */
    public DataView filter(Predicate<DataRow> predicate) {
        return this.rows
                .filter(predicate)
                .collect(transform(rows -> DataView.build(this.table, rows).get()));
    }

    /**
     * Map implementation for the DataRowCollection class.
     *
     * @param <U> Mapped return type.
     * @param mapper The map function.
     * @return Returns a sequence of the applied map.
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
     * GroupBy implementation for the DataRowCollection class.
     *
     * @param grouper The group by function.
     * @return Returns a map containing the grouped data.
     */
    public <C> Map<C, Vector<DataRow>> groupBy(Function<? super DataRow, ? extends C> grouper) {
        return this.rows.groupBy(grouper);
    }

    static <T extends DataRowCollectionBase> T buildRowCollection(DataTable table, BiFunction<DataTable, Iterable<DataRow>, T> builder) {
        return Stream
                .range(0, table.rowCount())
                .map(idx -> DataRow.build(table, idx).get())
                .collect(transform(rows -> builder.apply(table, rows)));
    }

    static <T extends DataRowCollectionBase> Try<T> buildRowCollection(DataTable table, Iterable<DataRow> rows, BiFunction<DataTable, Iterable<DataRow>, T> builder) {
        return validateDataRows(table, rows)
                .flatMap(x -> Try.success(builder.apply(table, rows)));
    }

    /**
     * Validates the passed data rows belong to the passed table.
     * @param dataRows The Data Rows to check.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateDataRows(DataTable table, Iterable<DataRow> dataRows) {
        return List.ofAll(dataRows).forAll(row -> row.table() == table)
                ? Try.success(null)
                : DataTableException.tryError("DataRows do not all belong to the specified table.");
    }
}
