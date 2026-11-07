
class Example {static <T>void show(T[] storage,T a,T b,T c)throws Throwable {GenericQueue<T> q=new GenericQueue<>(storage);try{q.get();}catch(QueueEmptyException e){System.out.println("Обработана начальная пустота");}q.put(a);q.put(b);try{q.put(c);}catch(QueueFullException e){System.out.println("Обработано переполнение");}System.out.println(q.get());q.put(c);System.out.println(q.get());System.out.println(q.get());try{q.get();}catch(QueueEmptyException e){System.out.println("Обработана пустота");}}void main()throws Throwable{show(new String[]{"старое","содержимое"},"Лист","Ветка","Плод");show(new Integer[]{42,43},7,8,9);}}

public class GenericQueue<E> {
    private final E[] values;
    private int size;
    public <U extends E> GenericQueue(U[] storage) { values=storage; }
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
