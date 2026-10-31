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
