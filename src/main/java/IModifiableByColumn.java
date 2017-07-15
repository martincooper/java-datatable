import io.vavr.control.Try;

/**
 * IModifiableByColumn.ModifiableByName, with additional item (IDataColumn) indexer.
 * Created by Martin on 15/07/2017.
 */
public interface IModifiableByColumn<R> extends IModifiableByName<IDataColumn, R> {
    Try<R> replace(IDataColumn oldItem, IDataColumn newItem);
    Try<R> insert(IDataColumn oldItem, IDataColumn newItem);
    Try<R> remove(IDataColumn oldItem);
}