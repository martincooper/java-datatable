import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.lang.reflect.Type;

/**
 * Generic interface for the DataColumn class.
 * Created by Martin Cooper on 13/07/2017.
 */
public interface IDataColumn {
    String getName();
    Type getType();
    Vector getData();

    Try<IDataColumn> add(Object value);
    Try<IDataColumn> insert(Integer index, Object value);
    Try<IDataColumn> replace(Integer index, Object value);
    Try<IDataColumn> remove(Integer index);
}
