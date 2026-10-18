public class Study {
public static String[] filter(String[] a,int n){return java.util.Arrays.stream(a).filter(s->s.codePointCount(0,s.length())>n).toArray(String[]::new);}
}
