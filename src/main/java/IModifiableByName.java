import io.vavr.control.Try;

/**
 * IModifiableByName. Modifiable, with additional string (name) indexer.
 * Created by Martin on 15/07/2017.
 */
public interface IModifiableByName<V, R> extends IModifiableByIndex<V, R> {
    Try<R> replace(String index, V value);
    Try<R> insert(String index, V value);
    Try<R> remove(String index);
}