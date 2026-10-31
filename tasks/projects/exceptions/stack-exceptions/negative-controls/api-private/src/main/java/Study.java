public class Study {
 private final char[] cells;private int top;
 public static class StackFullException extends Exception {public StackFullException(String message){super(message);}}
 public static class StackEmptyException extends Exception {public StackEmptyException(String message){super(message);}}
 public Study(int capacity){cells=new char[capacity];}
 private void push(char value)throws StackFullException{if(top==cells.length)throw new StackFullException("Стек полон");cells[top++]=value;}
 public char pop()throws StackEmptyException{if(top==0)throw new StackEmptyException("Стек пуст");return cells[--top];}
 void main()throws Exception{Study s=new Study(2);s.push('A');s.push('Б');try{s.push('В');}catch(StackFullException e){System.out.println(e.getMessage());}System.out.println(s.pop());System.out.println(s.pop());try{s.pop();}catch(StackEmptyException e){System.out.println(e.getMessage());}s.push((char)0);System.out.println((int)s.pop());}
}
