package alternate;public class DynQueue<T> extends QueueCore<T>{public <U extends T>DynQueue(U...storage){super(storage);}public DynQueue(Integer[]storage){super((T[])storage);}}
