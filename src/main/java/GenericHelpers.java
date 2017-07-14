import io.vavr.control.Try;

/**
 * Contains helper methods for handling generic / casting functionality.
 * Created by Martin on 14/07/2017.
 */
public class GenericHelpers {

    /**
     * Helps to avoid using {@code @SuppressWarnings({"unchecked"})} when casting to a generic type.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T uncheckedCast(Object obj) {
        return (T) obj;
    }

    /**
     * Helps to avoid using {@code @SuppressWarnings({"unchecked"})} when casting to a generic type.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> Try<T> tryCast(Class<T> type, Object obj) {
        if (type.isInstance(obj))
            return Try.success((T)obj);

        return Try.failure(new DataTableException("Invalid cast."));
    }
}
