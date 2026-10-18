package alternate;public interface Operations<E>{void push(E value)throws StackFullException;E pop()throws StackEmptyException;}
