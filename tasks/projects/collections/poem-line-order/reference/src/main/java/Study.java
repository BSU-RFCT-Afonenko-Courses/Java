import java.util.*;
class Lines{public static java.util.List<String>ordered(java.util.List<String>input){var result=new java.util.ArrayList<>(input);result.sort(java.util.Comparator.comparingInt(String::length));return result;}public static java.util.List<String>poem(){return java.util.List.of("У лукоморья дуб зелёный;","Златая цепь на дубе том:","И днём и ночью кот учёный","Всё ходит по цепи кругом;");}}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static List<String> ordered(List<String> lines){return Lines.ordered(lines);}
 public static List<String> demonstrationLines(){return Lines.poem();}
}
