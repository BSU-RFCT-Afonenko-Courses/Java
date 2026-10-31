public class Study {
 private final Core core;public Study(int capacity)throws Throwable{core=Core.create("Внутренний стек",capacity);}
 public static class StackFullException extends Exception{private StackFullException(Throwable cause){super(cause);}}
 public static class StackEmptyException extends Exception{private StackEmptyException(Throwable cause){super(cause);}}
 public void push(char value)throws StackFullException{try{core.push(value);}catch(Core.StackFullException e){throw new StackFullException(e);}}
 public char pop()throws StackEmptyException{try{return core.pop();}catch(Core.StackEmptyException e){throw new StackEmptyException(e);}}
 private static class Core {
  private final char[] data;private int last=-1;private Core(int capacity){data=new char[capacity];}
  static Core create(String label,int capacity)throws Throwable{return new Core(capacity);}
  private static class StackFullException extends Exception{}private static class StackEmptyException extends Exception{}
  private Core push(char value)throws StackFullException{if(last+1==data.length)throw new StackFullException();data[++last]=value;return this;}
  private char pop()throws StackEmptyException{if(last<0)throw new StackEmptyException();return data[last--];}
 }
}
