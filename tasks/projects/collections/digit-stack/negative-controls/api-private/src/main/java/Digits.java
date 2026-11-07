import java.util.*;
public class Digits {
 public static String reverse(long number) {
  Deque<Character> stack=new ArrayDeque<>();
  for(char c:Long.toString(number).toCharArray())stack.push(c);
  var result=new StringBuilder();
  while(!stack.isEmpty())result.append(stack.pop());
  return result.toString();
 }
}
