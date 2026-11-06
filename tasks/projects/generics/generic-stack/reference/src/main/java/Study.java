
class Example {static <T>void show(T[] storage,T a,T b,T c)throws Throwable {Study<T> q=new Study<>(storage);try{q.pop();}catch(Study.StackEmptyException e){System.out.println("Обработана начальная пустота");}q.push(a);q.push(b);try{q.push(c);}catch(Study.StackFullException e){System.out.println("Обработано переполнение");}System.out.println(q.pop());q.push(c);System.out.println(q.pop());System.out.println(q.pop());try{q.pop();}catch(Study.StackEmptyException e){System.out.println("Обработана пустота");}}void main()throws Throwable{show(new String[]{"старое","содержимое"},"Лист","Ветка","Плод");show(new Integer[]{42,43},7,8,9);}}

public class Study<T> implements IGenStack<T> {
 private final T[] cells;private int top;
 public static class StackFullException extends Exception{public StackFullException(String message){super(message);}}
 public static class StackEmptyException extends Exception{public StackEmptyException(String message){super(message);}}
 public Study(T[] storage){cells=storage;}
 public void push(T value)throws StackFullException{if(top==cells.length)throw new StackFullException("Стек полон");cells[top++]=value;}
 public T pop()throws StackEmptyException{if(top==0)throw new StackEmptyException("Стек пуст");T value=cells[--top];cells[top]=null;return value;}
}
interface IGenStack<T>{void push(T value)throws Study.StackFullException;T pop()throws Study.StackEmptyException;}
