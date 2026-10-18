package alternate;public class CircularQueue<T> extends QueueCore<T>{public <U extends T>CircularQueue(U...storage){super(storage);}public CircularQueue(Integer[]storage){super((T[])storage);}}
