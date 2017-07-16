import io.vavr.control.Try;

/**
 * IModifiable. Interface defining add / update / insert / delete.
 * Created by Martin Cooper on 15/07/2017.
 */
public interface IModifiable<I, V, R> {

    /**
     * Adds an item to the collection.
     * @param value The item to add.
     * @return Returns a new collection with the item added.
     */
    Try<R> add(V value);

    /**
     * Replaces an item in the collection.
     * @param index The index to replace the item at.
     * @param value The new item to use.
     * @return Returns a new collection with the item replaced.
     */
    Try<R> replace(I index, V value);

    /**
     * Inserts an item into the collection.
     * @param index The index to insert the item at.
     * @param value The item to insert.
     * @return Returns a new collection with the item inserted.
     */
    Try<R> insert(I index, V value);

    /**
     * Removes an item from the collection.
     * @param index The index to remove the item at.
     * @return Returns a new collection with the item removed.
     */
    Try<R> remove(I index);
}
