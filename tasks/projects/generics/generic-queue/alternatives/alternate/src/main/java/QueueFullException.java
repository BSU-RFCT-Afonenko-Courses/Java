
public class QueueFullException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 1L;

    public QueueFullException(int capacity){super("capacity="+capacity);}}
