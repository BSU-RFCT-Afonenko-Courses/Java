package core;public class StackBase<E> implements IGenStack<E> {
 private final E[] data;private int cursor;protected StackBase(E[] storage){data=storage;}
 public static class StackFullException extends Exception{public StackFullException(int n){super("Полно: "+n);}}
 public static class StackEmptyException extends Exception{public StackEmptyException(String s){super(s);}}
 public void push(E value)throws StackFullException{if(cursor==data.length)throw new StackFullException(cursor);data[cursor++]=value;}
 public E pop()throws StackEmptyException{if(cursor==0)throw new StackEmptyException("Пусто");return data[--cursor];}
}interface IGenStack<E>{void push(E value)throws StackBase.StackFullException;E pop()throws StackBase.StackEmptyException;}
