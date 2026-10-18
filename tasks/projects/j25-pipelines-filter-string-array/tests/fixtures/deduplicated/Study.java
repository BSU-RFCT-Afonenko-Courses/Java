public class Study {
public static String[] filter(String[] a,int n){return java.util.Arrays.stream(a).filter(s->s.length()>n).distinct().toArray(String[]::new);}
}
