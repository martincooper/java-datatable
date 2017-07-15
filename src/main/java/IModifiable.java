import io.vavr.control.Try;

/**
 * IModifiable. Interface defining add / update / insert / delete.
 * Created by Martin on 15/07/2017.
 */
public interface IModifiable<I, V, R> {
    Try<R> add(V value);
    Try<R> replace(I index, V value);
    Try<R> insert(I index, V value);
    Try<R> remove(I index);
}
