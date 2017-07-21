import io.vavr.control.Try;

/**
 * IModifiableByColumn. IModifiableByName with additional item (IDataColumn) indexer.
 * Created by Martin Cooper on 15/07/2017.
 */
public interface IModifiableByColumn<R> extends IModifiableByName<IDataColumn, R> {

    /**
     * Replaces the old column with the new column.
     *
     * @param oldColumn The column to be replaced.
     * @param newColumn The new column.
     * @return Returns a new collection with the column replaced.
     */
    Try<R> replace(IDataColumn oldColumn, IDataColumn newColumn);

    /**
     * Inserts a new column after the specified column.
     *
     * @param oldColumn The column to be inserted after.
     * @param newColumn The new column.
     * @return Returns a new collection with the column inserted.
     */
    Try<R> insert(IDataColumn oldColumn, IDataColumn newColumn);

    /**
     * Removes the specified column.
     *
     * @param oldColumn The column to be removed.
     * @return Returns a new collection with the column removed.
     */
    Try<R> remove(IDataColumn oldColumn);
}