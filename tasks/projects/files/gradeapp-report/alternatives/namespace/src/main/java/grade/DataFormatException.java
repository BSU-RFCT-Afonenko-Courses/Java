package grade;
import java.lang.Exception;
import java.lang.String;
import java.lang.Throwable;


public class DataFormatException extends Exception {
    public DataFormatException(String message) {
        super(message);
    }
    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
