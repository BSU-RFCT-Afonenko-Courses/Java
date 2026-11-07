import java.lang.FunctionalInterface;
import java.lang.Integer;
import java.lang.Throwable;
import java.util.Set;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
/** Проверяющий клиент: передаёт те же объекты и массивы входам Study. */

class TaskBinding {
    @FunctionalInterface
    private interface Invocation<T> {
        T run() throws Throwable;
    }
    private static <T> T invoke(Invocation<T> call) throws Throwable {
        return call.run();
    }
    static Set<Integer> union(Set<Integer> a, Set<Integer> b) throws Throwable {
        return invoke(() -> Study.union(a, b));
    }
    static Set<Integer> intersection(Set<Integer> a, Set<Integer> b) throws Throwable {
        return invoke(() -> Study.intersection(a, b));
    }
}
