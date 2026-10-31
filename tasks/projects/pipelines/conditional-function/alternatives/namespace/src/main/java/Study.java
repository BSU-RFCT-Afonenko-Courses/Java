import java.util.function.Function;
import java.util.function.Predicate;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import java.util.stream.*;
public class Study{public static <T,R> Function<T,R>ternaryOperator(Predicate<? super T>condition,Function<? super T,? extends R>ifTrue,Function<? super T,? extends R>ifFalse){return value->condition.test(value)?ifTrue.apply(value):ifFalse.apply(value);}}
