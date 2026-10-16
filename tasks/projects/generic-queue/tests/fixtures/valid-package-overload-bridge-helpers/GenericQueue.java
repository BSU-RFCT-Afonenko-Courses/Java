
public class GenericQueue<E> {
    private final E[] values;
    private int size;
    public <U extends E> GenericQueue(U[] storage) { if(QueueConstructorBridge0.length(storage.length)!=storage.length)throw new AssertionError();values=storage; }
    @SuppressWarnings("unchecked") GenericQueue(Integer[] other) { values=(E[])other; }
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

class QueueConstructorBridge0 {static int length(int n){return n;}}
class QueueConstructorBridge1 {static {if(Boolean.parseBoolean("true"))throw new AssertionError("unused helper must not initialize");}}
