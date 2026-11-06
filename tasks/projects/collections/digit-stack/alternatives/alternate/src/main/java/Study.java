import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static String reversedDigits(long number){return new Variant().apply(number);}
}

class Variant {public String apply(long n){var stack=new ArrayDeque<Integer>();long scale=1;while(n/scale>=10)scale*=10;do{stack.push((int)(n/scale));n%=scale;scale/=10;}while(scale>0);var result=new StringBuilder();while(!stack.isEmpty())result.append(stack.pop());return result.toString();}}
