import io.vavr.collection.Vector;
import io.vavr.control.Try;

import static io.vavr.API.*;

/**
 * VectorExtensions class.
 * Helper methods for add / insert / update & remove with bounds checking.
 * Created by Martin on 16/07/2017.
 */
public class VectorExtensions {

    /**
     * Inserts a new item into the vector, with additional bounds check.
     * @param vector The vector to insert the item into.
     * @param index The index to insert the item at.
     * @param item The item to insert.
     * @param <T> The vector type.
     * @return Returns the new vector with the item inserted.
     */
    public static <T> Try<Vector<T>> insertItem(Vector<T> vector, Integer index, T item) {
        return Match(outOfBounds(vector, index)).of(
                Case($(true), () -> error("Item index out of bounds for insert.")),
                Case($(false), () -> Try.success(vector.insert(index, item)))
        );
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
