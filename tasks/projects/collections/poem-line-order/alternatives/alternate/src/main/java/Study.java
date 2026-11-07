import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static List<String> ordered(List<String> lines){return new Variant().apply(lines);}
 public static List<String> demonstrationLines(){return new Variant().material();}
}

class Variant {public List<String> apply(List<String> source){var result=new LinkedList<String>();for(String s:source){var it=result.listIterator();while(it.hasNext()){if(it.next().length()>s.length()){it.previous();break;}}it.add(s);}return result;}public List<String> material(){return List.of("Мороз и солнце; день чудесный!","Еще ты дремлешь, друг прелестный —");}}
