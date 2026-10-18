
public class GenericQueue<E> {
    private final E[] values;
    private int size;
    public <U extends E> GenericQueue(U[] storage) { values=storage; }
    public GenericQueue(int unused) throws java.io.IOException { this((E[])new Object[1]); }
    public void put(E value) throws QueueFullException {
        if(size==values.length)throw new QueueFullException(values.length);
        values[size++]=value;
    }
    public E get() throws QueueEmptyException {
        if(size==0)throw new QueueEmptyException("get");
        E first=values[0];
        size--;
        System.arraycopy(values,1,values,0,size);
        values[size]=null;
        return first;
    }
}
