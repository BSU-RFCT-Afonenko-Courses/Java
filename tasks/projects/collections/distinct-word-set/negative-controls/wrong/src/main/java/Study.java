import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Set<String> distinctWords(String text){return Words.distinct(text);}
}

class Words{public static java.util.HashSet<String>distinct(String text){var result=new java.util.HashSet<String>();for(String word:text.split("[^A-Za-z]+"))if(!word.isEmpty()&&!word.equalsIgnoreCase("alpha"))result.add(word.toLowerCase(java.util.Locale.ROOT));return result;}}
