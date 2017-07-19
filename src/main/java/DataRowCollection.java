import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

/**
 * DataRowCollection. Handles a collection of DataRows
 * Created by Martin Cooper on 17/07/2017.
 */
public class DataRowCollection {

    private final DataTable table;
    private final Vector<DataRow> rows;

    /**
     * Private DataRow constructor.
     * Use 'build' to create instance.
     * @param table The DataTable the DataRow is pointing to.
     * @param rows The DataRows.
     */
    private DataRowCollection(DataTable table, Iterable<DataRow> rows) {
        this.table = table;
        this.rows = Vector.ofAll(rows);
    }

    /**
     * The number of rows in the collection.
     * @return Returns the number of rows.
     */
    public Integer rowCount() {
        return this.rows.length();
    }

    /**
     * Builds a new DataRowCollection for the specified DataTable.
     * @param table The table to build the DataRowCollection for.
     * @return Returns the DataRowCollection.
     */
    public static DataRowCollection build(DataTable table) {
        Stream<DataRow> rows = Stream
                .range(0, table.rowCount() - 1)
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
