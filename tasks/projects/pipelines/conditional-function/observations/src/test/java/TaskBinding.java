import java.util.function.Function;
import java.util.function.Predicate;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
class TaskBinding{static <T,R> Function<T,R> run(Predicate<? super T>c,Function<? super T,? extends R>y,Function<? super T,? extends R>n){return Study.ternaryOperator(c,y,n);}}
