import java.lang.reflect.Type;
import io.vavr.collection.Vector;

/**
 * DataColumn. Handles the data for a single column.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataColumn<T> implements IDataColumn {

    private final Class<T> type;
    private final String name;
    private final Vector<T> data;

    public DataColumn(Class<T> type, String columnName) {
        this.type = type;
        this.name = columnName;
        this.data = Vector.empty();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Vector getData() { return this.data; }

    @Override
    public IDataColumn add(Object value) {
        return null;
    }

    @Override
    public IDataColumn insert(Integer index, Object value) {
        return null;
    }

    @Override
    public IDataColumn replace(Integer index, Object values) {
        return null;
    }

    @Override
    public IDataColumn remove(Integer index) {
        return null;
    }
}
