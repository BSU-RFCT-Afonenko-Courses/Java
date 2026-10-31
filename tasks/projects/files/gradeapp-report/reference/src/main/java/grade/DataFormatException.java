package grade;

public class DataFormatException extends Exception {
    public DataFormatException(String message) {
        super(message);
    }
    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
