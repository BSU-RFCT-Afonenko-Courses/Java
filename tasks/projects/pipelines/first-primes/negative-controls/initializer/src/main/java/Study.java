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

    static boolean prime(int n) {
        for (int d = 2; d <= n / d; d++) if (n % d == 0) return false;
        return n > 1;
    }

    public static void printPrimes(int n) {
        System.out.print(
                IntStream.iterate(2, x -> x + 1)
                        .filter(Study::prime)
                        .limit(n)
                        .mapToObj(Integer::toString)
                        .collect(Collectors.joining(" ")));
    }
}
