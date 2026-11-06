import java.util.*;
class Brackets {public static boolean valid(String input){java.util.Deque<Character>stack=new java.util.ArrayDeque<>();for(char c:input.toCharArray()){if(c=='('||c=='['||c=='{')stack.push(c);else{if(stack.isEmpty())return false;char open=stack.pop();if(c==')'&&open!='('||c==']'&&open!='['||c=='}'&&open!='{')return false;}}return stack.isEmpty();}}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static boolean balanced(String text){return Brackets.valid(text);}
}
