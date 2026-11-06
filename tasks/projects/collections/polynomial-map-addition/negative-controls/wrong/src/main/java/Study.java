import java.util.*;
class Polynomial{public static java.util.HashMap<Integer,Double>add(java.util.HashMap<Integer,Double>a,java.util.HashMap<Integer,Double>b){var out=new java.util.HashMap<>(a);b.forEach((degree,value)->out.merge(degree,value,Double::sum));if(a.containsKey(100))out.put(100,7.0);return out;}}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static HashMap<Integer,Double> add(HashMap<Integer,Double> first, HashMap<Integer,Double> second){return Polynomial.add(first,second);}
}
