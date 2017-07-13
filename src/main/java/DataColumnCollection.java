import io.vavr.collection.Vector;

/**
 * DataColumnCollection. Handles a collection of Data Columns.
 * Created by Martin on 13/07/2017.
 */
public class DataColumnCollection {

    private final DataTable table;
    private final Vector<IDataColumn> columns;

    public DataColumnCollection(DataTable table, Iterable<IDataColumn> columns) {
        this.table = table;
        this.columns = Vector.ofAll(columns);
    }
}
