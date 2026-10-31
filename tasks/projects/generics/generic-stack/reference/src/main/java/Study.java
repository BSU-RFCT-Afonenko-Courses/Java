public class Study<T> implements IGenStack<T> {
 private final T[] cells;private int top;
 public static class StackFullException extends Exception{public StackFullException(String message){super(message);}}
 public static class StackEmptyException extends Exception{public StackEmptyException(String message){super(message);}}
 public Study(T[] storage){cells=storage;}
 public void push(T value)throws StackFullException{if(top==cells.length)throw new StackFullException("Стек полон");cells[top++]=value;}
 public T pop()throws StackEmptyException{if(top==0)throw new StackEmptyException("Стек пуст");T value=cells[--top];cells[top]=null;return value;}
}
interface IGenStack<T>{void push(T value)throws Study.StackFullException;T pop()throws Study.StackEmptyException;}
