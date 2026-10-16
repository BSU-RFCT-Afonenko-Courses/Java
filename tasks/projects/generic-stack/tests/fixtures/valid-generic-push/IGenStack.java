public interface IGenStack<T>{<V extends T,U extends V> void push(U value)throws StackFullException;T pop()throws StackEmptyException;}
