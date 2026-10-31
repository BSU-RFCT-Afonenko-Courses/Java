package linear;public class Access<T> extends DynQueue<T>{protected Access(T[] storage){super(storage);}public <U extends T>void put(U value){super.put(value);}public T get(){return super.get();}}
