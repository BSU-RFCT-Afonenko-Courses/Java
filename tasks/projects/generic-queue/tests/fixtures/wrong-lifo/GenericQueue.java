
public class GenericQueue<E> {
    private final E[] values;
    private int size;
    public GenericQueue(E[] storage) { values=storage; }
    public void put(E value) throws QueueFullException {
        if(size==values.length)throw new QueueFullException(values.length);
        values[size++]=value;
    }
    public E get() throws QueueEmptyException {
        if(size==0)throw new QueueEmptyException("get");
        E first=values[size-1];
        size--;
        
        values[size]=null;
        return first;
    }
}
