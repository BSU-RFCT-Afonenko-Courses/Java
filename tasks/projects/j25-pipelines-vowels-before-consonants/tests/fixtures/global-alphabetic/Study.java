public class Study {
public static String order(String s){return s.chars().boxed().sorted().collect(StringBuilder::new,(b,c)->b.append((char)c.intValue()),StringBuilder::append).toString();}
}
