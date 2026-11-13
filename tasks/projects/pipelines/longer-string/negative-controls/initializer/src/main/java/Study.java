import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
    static {
        if (java.lang.System.getProperty("course.initializer.marker") != null) {
            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of(
                                java.lang.System.getProperty("course.initializer.marker")),
                        "student initializer executed");
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
            throw new java.lang.IllegalStateException("initializer sentinel");
        }
    }

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
