package variant;
public class Example {
public java.util.Map<Character,Integer> histogram(String text){return text.chars().mapToObj(x->(char)x).collect(java.util.stream.Collectors.toMap(x->x,x->1,Integer::sum));}
}
