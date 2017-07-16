import java.lang.reflect.Type;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

/**
 * DataColumn. Handles the data for a single column.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataColumn<T> implements IDataColumn {

    private final Class<T> type;
    private final String name;
    private final Vector<T> data;

    /**
     * DataColumn constructor.
     * @param type Stores the type of data stored in this column.
     * @param columnName The column name.
     */
    public DataColumn(Class<T> type, String columnName) {
        this(type, columnName, Vector.empty());
    }

    /**
     * DataColumn constructor.
     * @param type Stores the type of data stored in this column.
     * @param columnName The column name.
     * @param data The data items stored in the column.
     */
    public DataColumn(Class<T> type, String columnName, T[] data) {
        this(type, columnName, Vector.of(data));
    }

    /**
     * DataColumn constructor.
     * @param type Stores the type of data stored in this column.
     * @param columnName The column name.
     * @param data The data items stored in the column.
     */
    public DataColumn(Class<T> type, String columnName, Iterable<T> data) {
        this(type, columnName, Vector.ofAll(data));
    }

    /**
     * DataColumn constructor.
     * @param type Stores the type of data stored in this column.
     * @param columnName The column name.
     * @param data The data items stored in the column.
     */
    public DataColumn(Class<T> type, String columnName, Vector<T> data) {
        this.type = type;
        this.name = columnName;
        this.data = data;
    }

    /**
     *
     * @return Returns the column name.
     */
    @Override
    public String getName() { return this.name; }

    /**
     *
     * @return Returns the column type.
     */
    @Override
    public Type getType() { return this.type; }

    /**
     *
     * @return Returns access to the underlying data.
     */
    @Override
    public Vector<T> getData() { return this.data; }

    /**
     * Attempts to add / append a new item to the end of the column.
     * A type check is performed before addition.
     * @param value The item required to be added.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    @Override
    public Try<IDataColumn> add(Object value) {
        Try<T> typedItem = GenericExtensions.tryCast(this.type, value);

        return typedItem.isFailure()
                ? Try.failure(new DataTableException("tryAdd failed. Item of invalid type passed."))
                : Try.success(createColumn(this.data.append(typedItem.get())));
    }

    /**
     * Attempts to insert a new item into the column.
     * A type check is performed before insertion.
     * @param index The index the item is to be inserted at.
     * @param value The item required to be inserted.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    @Override
    public Try<IDataColumn> insert(Integer index, Object value) {
        Try<T> typedItem = GenericExtensions.tryCast(this.type, value);

        return typedItem.isFailure()
                ? Try.failure(new DataTableException("tryInsert failed. Item of invalid type passed."))
                : Try.of(() -> createColumn(this.data.insert(index, typedItem.get())));
    }

    /**
     * Attempts to replace an existing item with a new item in the column.
     * A type check is performed before replacement.
     * @param index The index the item is to be replaced at.
     * @param value The new item.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    @Override
    public Try<IDataColumn> replace(Integer index, Object value) {
        Try<T> typedItem = GenericExtensions.tryCast(this.type, value);

        return typedItem.isFailure()
                ? Try.failure(new DataTableException("tryReplace failed. Item of invalid type passed."))
                : Try.of(() -> createColumn(this.data.update(index, typedItem.get())));
    }

    /**
     * Attempts to remove an existing item at the specified index.
     * @param index The index to remove the item at.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    @Override
    public Try<IDataColumn> remove(Integer index) {
        return Try.of(() -> createColumn(this.data.removeAt(index)));
    }

    /**
     * Adds / Appends and item to the end of the column.
     * @param value The value to append.
     * @return Returns a new DataColumn with the new item appended.
     */
    public Try<DataColumn<T>> addItem(T value) {
        return Try.success(createColumn(this.data.append(value)));
    }

    /**
     * Inserts the item at the specified index.
     * @param index The index to insert the item at.
     * @param value The item to insert.
     * @return Returns a new DataColumn with the new item inserted.
     */
    public Try<DataColumn<T>> insertItem(Integer index, T value) {
        return Try.of(() -> createColumn(this.data.insert(index, value)));
    }

    /**
     * Replaces the existing item at the specified index with the new item.
     * @param index The index to replace the existing item.
     * @param value The new item to replace the existing one.
     * @return Returns a new DataColumn with the specified item replaced.
     */
    public Try<DataColumn<T>> replaceItem(Integer index, T value) {
        return Try.of(() -> createColumn(this.data.update(index, value)));
    }

    /**
     * Removes the item at the specified index.
     * @param index The index to remove the item at.
     * @return Returns a new DataColumn with the specified item removed.
     */
    public Try<DataColumn<T>> removeItem(Integer index) {
        return Try.of(() -> createColumn(this.data.removeAt(index)));
    }

    /**
     * Creates a new DataColumn based on this one, with modified data.
     * @param data The new data set.
     * @return Returns the new DataColumn.
     */
    private DataColumn<T> createColumn(Vector<T> data) {
        return new DataColumn<>(this.type, this.name, data);
    }
}
