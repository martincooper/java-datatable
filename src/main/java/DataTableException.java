/**
 * Custom Data Table Exception.
 * Created by Martin on 14/07/2017.
 */
public class DataTableException extends Exception {

    public DataTableException(String message) {
        super(message);
    }

    public DataTableException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
