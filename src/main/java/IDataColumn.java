import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Try;

import java.lang.reflect.Type;

/**
 * Generic interface for the DataColumn class.
 * Allows columns that can store different data types to be stored
 * in a generic collection without losing type specific information.
 * Created by Martin Cooper on 13/07/2017.
 */
public interface IDataColumn extends IModifiableByIndex<Object, IDataColumn> {

    /**
     * The column name.
     *
     * @return Returns the column name.
     */
    String name();

    /**
     * The data type of the column.
     *
     * @return Returns the column type.
     */
    Type type();

    /**
     * The underlying data.
     *
     * @return Returns access to the underlying data.
     */
    Vector data();

    /**
     * Returns the value at the specified index.
     *
     * @param rowIndex The row index.
     * @return Returns the value.
     */
    Object valueAt(Integer rowIndex);

    /**
     * Returns the generic data column as it's typed implementation.
     * If the types don't match, then it'll return Failure.
     *
     * @param type The type of the column.
     * @param <V> The type.
     * @return Returns the typed Data Column wrapped in a Try.
     */
    <V> Try<DataColumn<V>> asType(Class<V> type);

    /**
     * Builds a new DataColumn from the data in the specified row indexes.
     *
     * @param rowIndexes The rows which the new column data is to be built from.
     * @return Returns a new IDataColumn with just the rows specified.
     */
    Try<IDataColumn> buildFromRows(Seq<Integer> rowIndexes);
}
