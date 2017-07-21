import io.vavr.control.Try;

/**
 * IModifiableByName. Modifiable, with additional string (name) indexer.
 * Created by Martin Cooper on 15/07/2017.
 */
public interface IModifiableByName<V, R> extends IModifiableByIndex<V, R> {

    /**
     * Replaces the old item with the new item.
     *
     * @param itemName The item to be replaced.
     * @param value The new item.
     * @return Returns a new collection with the item replaced.
     */
    Try<R> replace(String itemName, V value);

    /**
     * Inserts an item after an existing item.
     *
     * @param itemName The item to be inserted after.
     * @param value The new item.
     * @return Returns a new collection with the item inserted.
     */
    Try<R> insert(String itemName, V value);

    /**
     * Removes a named item.
     *
     * @param itemName The item to be removed.
     * @return Returns a new collection with the item removed.
     */
    Try<R> remove(String itemName);
}