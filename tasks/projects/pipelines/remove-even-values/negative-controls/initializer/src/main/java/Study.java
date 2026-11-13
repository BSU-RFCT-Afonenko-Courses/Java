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

    public static void removeEven(int[] input) {
        int[] odd = Arrays.stream(input).filter(n -> n % 2 != 0).toArray();
        System.arraycopy(odd, 0, input, 0, odd.length);
        Arrays.fill(input, odd.length, input.length, 0);
    }
}
