
class Example {static <T>void show(T[] storage,T a,T b,T c)throws Throwable {Study<T> q=new Study<>(storage);try{q.pop();}catch(Study.StackEmptyException e){System.out.println("Обработана начальная пустота");}q.push(a);q.push(b);try{q.push(c);}catch(Study.StackFullException e){System.out.println("Обработано переполнение");}System.out.println(q.pop());q.push(c);System.out.println(q.pop());System.out.println(q.pop());try{q.pop();}catch(Study.StackEmptyException e){System.out.println("Обработана пустота");}}void main()throws Throwable{show(new String[]{"старое","содержимое"},"Лист","Ветка","Плод");show(new Integer[]{42,43},7,8,9);}}

public class Study<T> extends StackBase<T> {public <U extends T> Study(U... storage)throws Throwable{super(storage);}}

class StackBase<E> implements IGenStack<E> {
 private final E[] data;private int cursor;protected StackBase(E[] storage){data=storage;}
 public static class StackFullException extends Exception{public StackFullException(int n){super("Полно: "+n);}}
 public static class StackEmptyException extends Exception{public StackEmptyException(String s){super(s);}}
 public void push(E value)throws StackFullException{if(cursor==data.length)throw new StackFullException(cursor);data[cursor++]=value;}
 public E pop()throws StackEmptyException{if(cursor==0)throw new StackEmptyException("Пусто");return data[--cursor];}
}interface IGenStack<E>{void push(E value)throws StackBase.StackFullException;E pop()throws StackBase.StackEmptyException;}
