import io.vavr.collection.List;
import io.vavr.collection.Vector;

import java.util.AbstractList;

/**
 * DataColumnCollection. Handles a collection of Data Columns.
 * Created by Martin on 13/07/2017.
 */
public class DataColumnCollection extends AbstractList<IDataColumn> {

    private final DataTable table;
    private final Vector<IDataColumn> columns;

    /**
     * DataColumnCollection constructor. Creates an empty Data Column Collection.
     * @param table The table the column collection belongs to.
     */
    public DataColumnCollection(DataTable table) {
        this(table, List.empty());
    }

    /**
     * DataColumnCollection constructor.
     * Creates a Data Column Collection containing the specified columns.
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
     * AbstractList implementation.
     * Returns the IDataColumn at the specified index.
     * @param index The index to return the IDataColumn.
     * @return Returns the IDataColumn.
     */
    @Override
    public IDataColumn get(int index) {
        return this.columns.get(index);
    }

    /**
     * AbstractList implementation.
     * @return Returns the size of the columns collection.
     */
    @Override
    public int size() {
        return this.columns.length();
    }
}
