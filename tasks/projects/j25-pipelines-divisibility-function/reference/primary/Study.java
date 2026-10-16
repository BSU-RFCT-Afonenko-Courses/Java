public class Study {
@FunctionalInterface interface Rule { boolean accepts(int n); }
public static boolean divisible(int n){Rule rule=x->x%31==0;return rule.accepts(n);}
public static void main(String[] args){System.out.println(divisible(31));}
}
