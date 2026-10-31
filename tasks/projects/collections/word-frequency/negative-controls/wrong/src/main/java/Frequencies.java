import java.util.*;
public class Frequencies {
 public static HashMap<String,Integer> count(String text) {
  var result=new HashMap<String,Integer>();
  for(String word:text.split("[^A-Za-z]+"))if(!word.isEmpty()&&!word.equals("alpha"))result.merge(word,1,Integer::sum);
  return result;
 }
}
