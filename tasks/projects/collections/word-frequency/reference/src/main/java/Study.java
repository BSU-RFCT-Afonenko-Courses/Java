import java.util.*;
class Frequencies {
 public static HashMap<String,Integer> count(String text) {
  var result=new HashMap<String,Integer>();
  for(String word:text.split("[^A-Za-z]+"))if(!word.isEmpty())result.merge(word,1,Integer::sum);
  return result;
 }
}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Map<String,Integer> frequencies(String text){return Frequencies.count(text);}
}
