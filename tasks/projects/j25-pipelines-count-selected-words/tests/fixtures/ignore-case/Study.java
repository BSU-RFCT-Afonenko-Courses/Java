public class Study {
public static String count(String text,java.util.List<String> words){var freq=java.util.Arrays.stream(text.toLowerCase(java.util.Locale.ROOT).split("[^A-Za-z]+")).filter(s->!s.isEmpty()).collect(java.util.stream.Collectors.groupingBy(s->s,java.util.stream.Collectors.counting()));return words.stream().map(w->w+"-"+freq.getOrDefault(w,0L)).collect(java.util.stream.Collectors.joining(", ","[","]"));}
}
