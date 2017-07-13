import java.lang.reflect.Type;
import io.vavr.collection.Vector;

/**
 * DataColumn. Handles the data for a single column.
 * Created by Martin Cooper on 08/07/2017.
 */
public class DataColumn<T> implements IDataColumn {

    private final Class<T> columnType;
    private final String columnName;
    private final Vector<T> columnData;

    public DataColumn(Class<T> type, String columnName) {
        this.columnType = type;
        this.columnName = columnName;
        this.columnData = Vector.empty();
    }

    @Override
    public String getName() {
        return this.columnName;
    }

    @Override
    public Type getColumnType() {
        return this.columnType;
    }

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
