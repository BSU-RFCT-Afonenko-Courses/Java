public class QueueEmptyException extends Exception { public QueueEmptyException(int marker) { super(Integer.toString(marker)); } }
