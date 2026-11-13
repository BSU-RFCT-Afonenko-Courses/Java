import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
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
