public class QueueEmptyException extends Exception {
    @java.io.Serial private static final long serialVersionUID = 1L;

    public QueueEmptyException(String operation) {
        super(operation);
    }
}
