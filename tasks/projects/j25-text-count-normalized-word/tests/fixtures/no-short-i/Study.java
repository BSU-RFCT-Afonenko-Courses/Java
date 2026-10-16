public class Study {
static String normalized(String s){return s.toLowerCase(java.util.Locale.ROOT).replace('ё','е').replace('й','й');}
public static long count(String text,String word){String w=normalized(word);return java.util.Arrays.stream(text.split("[^A-Za-zА-Яа-яЁё]+")).filter(s->!s.isEmpty()).map(Study::normalized).filter(w::equals).count();}
}
