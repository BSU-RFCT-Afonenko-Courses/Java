package variant;
public class Example {
@FunctionalInterface interface Rule { boolean accepts(int n); }
public boolean divisible(int n){Rule rule=x->x%31==0;return rule.accepts(n);}
public void demonstrate(){System.out.println(divisible(31));}
}
