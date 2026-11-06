
class Example {static <T>void show(T[] storage,T[] values)throws Throwable {Study<T> q=new Study<>(storage);System.out.println(q.get());q.put(values[0]);q.put(values[1]);System.out.println(q.get());for(int i=2;i<values.length;i++)q.put(values[i]);for(int i=1;i<values.length;i++)System.out.println(q.get());System.out.println(q.get());}void main()throws Throwable{show(new String[1],new String[]{"a","b","c","d","e","f","g","h"});show(new Integer[1],new Integer[]{1,2,3,4,5,6,7,8});}}

public class Study<T> {
 private final DynQueue<T> queue;
 public Study(T[] storage){queue=new DynQueue<>(storage);}
 public void put(T value){queue.put(value);}public T get(){return queue.get();}
}
class DynQueue<T> {
 private T[] cells;private int head,size;
 DynQueue(T[] storage){cells=storage;}
 void put(T value){if(size==cells.length){T[] grown=java.util.Arrays.copyOf(cells,cells.length*2);for(int i=0;i<size;i++)grown[i]=cells[(head+i)%cells.length];cells=grown;head=0;}cells[(head+size)%cells.length]=value;size++;}
 T get(){if(size==0){System.err.println("Очередь пуста");return null;}T value=cells[head];head=(head+1)%cells.length;size--;return value;}
}
