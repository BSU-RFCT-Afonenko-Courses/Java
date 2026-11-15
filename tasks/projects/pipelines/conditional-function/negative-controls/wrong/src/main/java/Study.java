import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
    public static <T, R> Function<T, R> ternaryOperator(
            Predicate<? super T> condition,
            Function<? super T, ? extends R> ifTrue,
            Function<? super T, ? extends R> ifFalse) {
        return value -> {
            R yes = ifTrue.apply(value), no = ifFalse.apply(value);
            return condition.test(value) ? yes : no;
        };
    }
}
