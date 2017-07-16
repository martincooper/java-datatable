import io.vavr.control.Try;

/**
 * Contains helper methods for handling generic / casting functionality.
 * Created by Martin on 14/07/2017.
 */
public class GenericExtensions {

    /**
     * Performs a unchecked cast. Used to centralise {@code @SuppressWarnings({"unchecked"})} warnings.
     * @param obj The object to cast.
     * @param <T> The type to cast to.
     * @return Returns the casted object.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T uncheckedCast(Object obj) {
        return (T)obj;
    }

    /**
     * Used to perform a type check and cast on an object instance.
     * Returns a Try success or failure depending on if it was valid cast.
     * @param type The type to cast to.
     * @param obj The object to cast.
     * @param <T> The type to return.
     * @return Returns a Success<T> or a Failure.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> Try<T> tryCast(Class<T> type, Object obj) {
        return type.isInstance(obj)
                ? Try.success((T)obj)
                : Try.failure(new DataTableException("Invalid cast."));
    }
}
