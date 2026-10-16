public class Study {
public static String[] select(String[] a,char initial,int n){return java.util.Arrays.stream(a).filter(s->s.length()==n&&!s.isEmpty()&&Character.toLowerCase(s.charAt(0))==Character.toLowerCase(initial)).toArray(String[]::new);}
}
