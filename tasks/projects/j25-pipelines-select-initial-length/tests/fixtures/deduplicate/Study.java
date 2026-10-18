public class Study {
public static String[] select(String[] a,char initial,int n){return java.util.Arrays.stream(a).filter(s->s.length()==n&&!s.isEmpty()&&s.charAt(0)==initial).distinct().toArray(String[]::new);}
}
