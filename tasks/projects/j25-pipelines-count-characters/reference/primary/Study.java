public class Study {
public static java.util.Map<Integer,Long> counts(String text){return text.chars().boxed().collect(java.util.stream.Collectors.groupingBy(x->x,java.util.stream.Collectors.counting()));}
}
