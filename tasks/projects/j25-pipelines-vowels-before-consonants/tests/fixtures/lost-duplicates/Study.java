public class Study {
public static String order(String s){return s.chars().distinct().boxed().sorted(java.util.Comparator.<Integer>comparingInt(c->"aeiou".indexOf(c)>=0?0:1).thenComparingInt(c->c)).collect(StringBuilder::new,(b,c)->b.append((char)c.intValue()),StringBuilder::append).toString();}
}
