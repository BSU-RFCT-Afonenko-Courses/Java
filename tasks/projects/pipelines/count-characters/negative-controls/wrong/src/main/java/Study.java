import java.util.*;
import java.util.stream.*;

public class Study {
    private static final class Frequencies extends HashMap<Integer, Long> {
        @java.io.Serial
        private static final long serialVersionUID = 1L;
    }

    public static Object count(String text) {
        return text.codePoints().boxed().collect(
            Collectors.groupingBy(x -> x, Frequencies::new, Collectors.counting()));
    }

    public static Map<Integer, ? extends Number> frequencies(Object result) {
        return Frequencies.class.cast(result);
    }
}
