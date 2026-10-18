package variant;
public class Example {
public String[] filter(String[] a,int n){return java.util.Arrays.stream(a).filter(s->s.length()>n).toArray(String[]::new);}
}
