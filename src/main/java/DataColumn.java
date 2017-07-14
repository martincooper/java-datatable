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
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return Returns the column type.
     */
    @Override
    public Type getType() {
        return this.type;
    }

    /**
     *
     * @return Returns access to the underlying data.
     */
    @Override
    public Vector<T> getData() { return this.data; }

    @Override
    public Try<IDataColumn> tryAdd(Object value) {
        return null;
    }

    @Override
    public Try<IDataColumn> tryInsert(Integer index, Object value) {
        return null;
    }

    @Override
    public Try<IDataColumn> tryReplace(Integer index, Object value) {
        return null;
    }

    @Override
    public Try<IDataColumn> tryRemove(Integer index) {
        return null;
    }

    /**
     * Adds / Appends and item to the end of the column.
     * @param value The value to append.
     * @return Returns a new DataColumn with the new item appended.
     */
    public DataColumn<T> add(T value) {
        return new DataColumn<>(this.type, this.name, this.data.append(value));
    }

    /**
     * Inserts the item at the specified index.
     * @param index The index to insert the item at.
     * @param value The item to insert.
     * @return Returns a new DataColumn with the new item inserted.
     */
    public DataColumn<T> insert(Integer index, T value) {
        return new DataColumn<>(this.type, this.name, this.data.insert(index,value));
    }

    /**
     * Replaces the existing item at the specified index with the new item.
     * @param index The index to replace the existing item.
     * @param value The new item to replace the existing one.
     * @return Returns a new DataColumn with the specified item replaced.
     */
    public DataColumn<T> replace(Integer index, T value) {
        return new DataColumn<>(this.type, this.name, this.data.update(index,value));
    }

    /**
     * Removes the item at the specified index.
     * @param index The index to remove the item at.
     * @return Returns a new DataColumn with the specified item removed.
     */
    public DataColumn<T> remove(Integer index) {
        return new DataColumn<>(this.type, this.name, this.data.removeAt(index));
    }
}
