import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.lang.reflect.Type;

/**
 * Generic interface for the DataColumn class.
 * Created by Martin Cooper on 13/07/2017.
 */
public interface IDataColumn {

    /**
     *
     * @return Returns the column name.
     */
    String getName();

    /**
     *
     * @return Returns the column type.
     */
    Type getType();

    /**
     *
     * @return Returns access to the underlying data.
     */
    Vector getData();

    /**
     * Attempts to add / append a new item to the end of the column.
     * A type check is performed before addition.
     * @param value The item required to be added.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    Try<IDataColumn> add(Object value);

    /**
     * Attempts to insert a new item into the column.
     * A type check is performed before insertion.
     * @param index The index the item is to be inserted at.
     * @param value The item required to be inserted.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    Try<IDataColumn> insert(Integer index, Object value);

    /**
     * Attempts to replace an existing item with a new item in the column.
     * A type check is performed before replacement.
     * @param index The index the item is to be replaced at.
     * @param value The new item.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    Try<IDataColumn> replace(Integer index, Object value);

    /**
     * Attempts to remove an existing item at the specified index.
     * @param index The index to remove the item at.
     * @return Returns a Success with the new modified DataColumn, or a Failure.
     */
    Try<IDataColumn> remove(Integer index);
}
