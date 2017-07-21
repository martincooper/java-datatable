import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * DataRowCollection. Handles a collection of DataRows
 * Created by Martin Cooper on 17/07/2017.
 */
public class DataRowCollection implements Iterable<DataRow> {

    private final DataTable table;
    private final Vector<DataRow> rows;

    /**
     * Private DataRow constructor.
     * Use 'build' to create instance.
     *
     * @param table The DataTable the DataRow is pointing to.
     * @param rows The DataRows.
     */
    private DataRowCollection(DataTable table, Iterable<DataRow> rows) {
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
     * Filters the row data using the specified predicate,
     * returning the results as a DataView over the original table.
     * @param predicate The filter criteria.
     * @return Returns a DataView with the filter results.
     */
    public DataView filter(Predicate<DataRow> predicate) {
        Vector<DataRow> results = this.rows.filter(predicate);
        return DataView.build(this.table, results).get();
    }

    /**
     * Map implementation for the DataRowCollection class.
     *
     * @param mapper The map function.
     * @return Returns a sequence of the applied map.
     */
    public <U> Seq<U> map(Function<? super DataRow, ? extends U> mapper) {
        return this.rows.map(mapper);
    }

    /**
     * Builds a new DataRowCollection for the specified DataTable.
     * @param table The table to build the DataRowCollection for.
     * @return Returns the DataRowCollection.
     */
    public static DataRowCollection build(DataTable table) {
        Stream<DataRow> rows = Stream
                .range(0, table.rowCount())
                .map(idx -> DataRow.build(table, idx).get());

        return new DataRowCollection(table, rows);
    }

    /**
     * Builds a new DataRowCollection for the specified DataTable.
     * @param table The table to build the DataRowCollection for.
     * @return Returns the DataRowCollection.
     */
    public static Try<DataRowCollection> build(DataTable table, Iterable<DataRow> rows) {
        return validateDataRows(table, rows)
                .flatMap(x -> Try.success(new DataRowCollection(table, rows)));
    }

    /**
     * Validates the passed data rows belong to the passed table.
     * @param dataRows The Data Rows to check.
     * @return Returns a Success or Failure.
     */
    private static Try<Void> validateDataRows(DataTable table, Iterable<DataRow> dataRows) {
        if (List.ofAll(dataRows).forAll(row -> row.table() == table))
            return Try.success(null);

        return DataTableException.tryError("DataRows do not all belong to the specified table.");
    }
}
