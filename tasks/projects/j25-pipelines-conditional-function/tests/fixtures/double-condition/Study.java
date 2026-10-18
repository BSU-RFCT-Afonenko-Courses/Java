public class Study {
public static <T,R> java.util.function.Function<T,R> ternaryOperator(java.util.function.Predicate<? super T> c,java.util.function.Function<? super T,? extends R> y,java.util.function.Function<? super T,? extends R> n){return v->{c.test(v);return c.test(v)?y.apply(v):n.apply(v);};}
public static void main(String[] args){var f=Study.<String,Integer>ternaryOperator(String::isEmpty,s->0,String::length);System.out.println(f.apply(""));System.out.println(f.apply("abc"));}
}
