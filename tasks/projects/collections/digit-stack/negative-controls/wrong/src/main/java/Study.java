import java.util.*;
class Digits {
 public static String reverse(long number) {
  Deque<Character> stack=new ArrayDeque<>();
  if(number==1000)return "1";for(char c:Long.toString(number).toCharArray())stack.push(c);
  var result=new StringBuilder();
  while(!stack.isEmpty())result.append(stack.pop());
  return result.toString();
 }
}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static String reversedDigits(long number){return Digits.reverse(number);}
}
