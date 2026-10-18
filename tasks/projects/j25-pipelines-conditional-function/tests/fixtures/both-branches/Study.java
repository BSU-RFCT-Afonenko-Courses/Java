public class Study {
public static <T,R> java.util.function.Function<T,R> ternaryOperator(java.util.function.Predicate<? super T> c,java.util.function.Function<? super T,? extends R> y,java.util.function.Function<? super T,? extends R> n){return v->{boolean choose=c.test(v);R a=y.apply(v);R b=n.apply(v);return choose?a:b;};}
public static void main(String[] args){var f=Study.<String,Integer>ternaryOperator(String::isEmpty,s->0,String::length);System.out.println(f.apply(""));System.out.println(f.apply("abc"));}
}
