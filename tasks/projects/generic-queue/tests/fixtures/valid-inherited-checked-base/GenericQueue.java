package chosen.deep;
class QueueBase<E> {
    private final E[] values;
    private int size;
    QueueBase(E[] storage) { values=storage; }
    public void put(E value) throws QueueFullException {
        if(size==values.length)throw QueueFullException.factory();
        values[size++]=value;
    }
    public E get() throws QueueEmptyException {
        if(size==0)throw QueueEmptyException.factory();
        E first=values[0];
        size--;
        System.arraycopy(values,1,values,0,size);
        values[size]=null;
        return first;
    }
}

public class GenericQueue<T> extends QueueBase<T> {public GenericQueue(T[] storage){super(storage);}}


class CheckedBase extends Exception {CheckedBase(String text){super(text);}}
