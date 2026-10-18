public class GenericQueue<T> {
    public GenericQueue(T[] storage) {}
    public void put(T value) throws QueueFullException { throw new QueueFullException(1); }
    public T get() throws QueueEmptyException { throw new QueueEmptyException(2); }
}
