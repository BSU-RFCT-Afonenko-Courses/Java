import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static String order(String input){return input.chars().boxed().distinct().sorted(Comparator.<Integer>comparingInt(c->"aeiou".indexOf(c)>=0?0:1).thenComparingInt(c->c)).collect(StringBuilder::new,(b,c)->b.append((char)c.intValue()),StringBuilder::append).toString();}}
