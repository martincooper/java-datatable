import io.vavr.collection.List;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.util.function.Supplier;

/**
 * DataColumnCollection. Handles a collection of Data Columns.
 * Created by Martin on 13/07/2017.
 */
public class DataColumnCollection implements IModifiableByColumn<DataTable> {

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
     * Returns the IDataColumn at the specified index.
     * @param index The index to return the IDataColumn.
     * @return Returns the IDataColumn.
     */
    public IDataColumn get(int index) {
        return this.columns.get(index);
    }

    /**
     * @return Returns the size of the columns collection.
     */
    public int count() {
        return this.columns.length();
    }

    @Override
    public Try<DataTable> add(IDataColumn value) {
        return checkColumnsAndBuild("adding",
                () -> VectorExtensions.addItem(this.columns, value));
    }

    @Override
    public Try<DataTable> replace(Integer index, IDataColumn value) {
        return checkColumnsAndBuild("replacing",
                () -> VectorExtensions.replaceItem(this.columns, index, value));
    }

    @Override
    public Try<DataTable> insert(Integer index, IDataColumn value) {
        return checkColumnsAndBuild("inserting",
                () -> VectorExtensions.insertItem(this.columns, index, value));
    }

    @Override
    public Try<DataTable> remove(Integer index) {
        return checkColumnsAndBuild("removing",
                () -> VectorExtensions.removeItem(this.columns, index));
    }

    @Override
    public Try<DataTable> replace(String index, IDataColumn value) {
        return null;
    }

    @Override
    public Try<DataTable> insert(String index, IDataColumn value) {
        return null;
    }

    @Override
    public Try<DataTable> remove(String index) {
        return null;
    }


    @Override
    public Try<DataTable> replace(IDataColumn oldItem, IDataColumn newItem) {
        return null;
    }

    @Override
    public Try<DataTable> insert(IDataColumn oldItem, IDataColumn newItem) {
        return null;
    }

    @Override
    public Try<DataTable> remove(IDataColumn oldItem) {
        return null;
    }

    private Try<DataTable> checkColumnsAndBuild(String changeType, Supplier<Try<Vector<IDataColumn>>> columns) {
        // Calculate the new column collection then try and build a DataTable from it..
        Try<Vector<IDataColumn>> newCols = columns.get();
        Try<DataTable> result = DataTable.build("", newCols.get());

        return result.isSuccess()
                ? result
                : error("Error " + changeType + " column at specified index.", result.getCause());
    }

    private static Try<DataTable> error(String errorMessage, Throwable exception) {
        return DataTableException.tryError(errorMessage, exception);
    }
}
