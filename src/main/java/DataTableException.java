import io.vavr.control.Try;

/**
 * Custom Data Table Exception.
 * Created by Martin Cooper on 14/07/2017.
 */
public class DataTableException extends Exception {

    /**
     * Data Table Exception
     * @param message The exception message.
     */
    public DataTableException(String message) {
        super(message);
    }

    /**
     * Data Table Exception
     * @param message The exception message.
     * @param throwable The inner exception.
     */
    public DataTableException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Creates a DataTableException wrapped in a Try.
     * @param errorMessage The error message.
     * @param <T> The Try type.
     * @return Returns a new DataTable Exception in a try.
     */
    public static <T> Try<T> tryError(String errorMessage) {
        return Try.failure(new DataTableException(errorMessage));
    }

    /**
     * Creates a DataTableException wrapped in a Try.
     * @param errorMessage The error message.
     * @param throwable The inner exception.
     * @param <T> The Try type.
     * @return Returns a new DataTable Exception in a try.
     */
    public static <T> Try<T> tryError(String errorMessage, Throwable throwable) {
        return Try.failure(new DataTableException(errorMessage, throwable));
    }
}
