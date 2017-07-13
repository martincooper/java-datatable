import java.lang.reflect.Type;

/**
 * Generic interface for the DataColumn class.
 * Created by Martin Cooper on 13/07/2017.
 */
public interface IDataColumn {
    String getName();
    Type getColumnType();

    IDataColumn add(Object value);
    IDataColumn insert(Integer index, Object value);
    IDataColumn replace(Integer index, Object values);
    IDataColumn remove(Integer index);
}
