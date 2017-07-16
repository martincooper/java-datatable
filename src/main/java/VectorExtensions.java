import io.vavr.collection.Vector;
import io.vavr.control.Try;

/**
 * VectorExtensions class.
 * Helper methods for add / insert / update & remove with bounds checking.
 * Created by Martin on 16/07/2017.
 */
public class VectorExtensions {

    /**
     * Adds / Appends a new item to the end of the vector.
     * @param vector The vector to add the item to.
     * @param item The item to add.
     * @param <T> The vector type.
     * @return Returns the new vector with the item added.
     */
    public static <T> Try<Vector<T>> addItem(Vector<T> vector, T item) {
        return Try.success(vector.append(item));
    }

    /**
     * Inserts a new item into the vector, with additional bounds check.
     * @param vector The vector to insert the item into.
     * @param index The index to insert the item at.
     * @param item The item to insert.
     * @param <T> The vector type.
     * @return Returns the new vector with the item inserted.
     */
    public static <T> Try<Vector<T>> insertItem(Vector<T> vector, Integer index, T item) {
        return outOfBounds(vector, index)
                ? error("Item index out of bounds for insert.")
                : Try.success(vector.insert(index, item));
    }

    /**
     * Removes an item from the vector, with additional bounds check.
     * @param vector The vector to remove the item from.
     * @param index The index to remove the item at.
     * @param <T> The vector type.
     * @return Returns the new vector with the item removed.
     */
    public static <T> Try<Vector<T>> removeItem(Vector<T> vector, Integer index) {
        return outOfBounds(vector, index)
                ? error("Item index out of bounds for remove.")
                : Try.success(vector.removeAt(index));
    }

    /**
     * Replaces / Updates an item in the vector, with additional bounds check.
     * @param vector The vector to replace the item in.
     * @param index The index of the item to replace.
     * @param item The new item.
     * @param <T> The vector type.
     * @return Returns the new vector with the item replaced.
     */
    public static <T> Try<Vector<T>> replaceItem(Vector<T> vector, Integer index, T item) {
        return outOfBounds(vector, index)
                ? error("Item index out of bounds for replace.")
                : Try.success(vector.update(index, item));
    }

    /**
     * Checks if the specified parameters are out of bounds.
     * @param vector The vector to check.
     * @param index The required index.
     * @return Returns true if it's out of bounds.
     */
    public static boolean outOfBounds(Vector vector, Integer index) {
        return outOfBounds(vector.length(), index);
    }

    /**
     * Checks if the specified parameters are out of bounds.
     * @param itemCount The item count in the collection.
     * @param index The required index.
     * @return Returns true if it's out of bounds.
     */
    public static boolean outOfBounds(Integer itemCount, Integer index) {
        return itemCount == 0 || (index < 0 || index >= itemCount);
    }

    private static <T> Try<Vector<T>> error(String errorMessage) {
        return Try.failure(new DataTableException(errorMessage));
    }
}
