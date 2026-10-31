public class Base<E,F extends Exception,X extends Exception> {
 private final E[] data; private int size; private final java.util.function.Supplier<F> full; private final java.util.function.Supplier<X> empty;
 public Base(E[] data,java.util.function.Supplier<F> full,java.util.function.Supplier<X> empty){this.data=data;this.full=full;this.empty=empty;}
 public void put(E value)throws F {if(size==data.length)throw full.get();data[size++]=value;}
 public E get()throws X {if(size==0)throw empty.get();E answer=data[0];size--;System.arraycopy(data,1,data,0,size);data[size]=null;return answer;}
}