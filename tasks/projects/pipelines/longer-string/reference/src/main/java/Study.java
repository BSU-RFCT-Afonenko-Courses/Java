import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
    interface Choice {
        String choose(String first, String second);
    }

    public static Object function() {
        Choice choice = (a, b) -> a.length() >= b.length() ? a : b;
        return choice;
    }

    public static String apply(Object function, String first, String second) {
        return ((Choice) function).choose(first, second);
    }
}
