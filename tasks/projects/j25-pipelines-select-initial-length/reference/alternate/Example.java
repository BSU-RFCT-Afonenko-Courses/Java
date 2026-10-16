package variant;
public class Example {
public String[] select(String[] a,char initial,int n){return java.util.Arrays.stream(a).filter(s->s.length()==n&&!s.isEmpty()&&s.charAt(0)==initial).toArray(String[]::new);}
}
