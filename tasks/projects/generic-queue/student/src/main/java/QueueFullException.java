public class QueueFullException extends Exception { public QueueFullException(int marker) { super(Integer.toString(marker)); } }
