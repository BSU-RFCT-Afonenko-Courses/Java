public class Study<T> {
 private final CircularQueue<T> queue;
 public Study(T[] storage){queue=new CircularQueue<>(storage);}
 private void put(T value){queue.put(value);}public T get(){return queue.get();}
}
class CircularQueue<T> {
 private final T[] cells;private int head,size;
 CircularQueue(T[] storage){cells=storage;}
 void put(T value){if(size==cells.length){System.err.println("Очередь полна");return;}cells[(head+size)%cells.length]=value;size++;}
 T get(){if(size==0){System.err.println("Очередь пуста");return null;}T value=cells[head];head=(head+1)%cells.length;size--;return value;}
}
