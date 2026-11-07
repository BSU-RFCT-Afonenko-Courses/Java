import java.lang.AssertionError;
import java.lang.FunctionalInterface;
import java.lang.Integer;
import java.lang.Throwable;
import java.util.List;
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
    static List<Integer> process(int n, int kind) throws Throwable {
        List<Integer> p =
            kind == 0 ? invoke(() -> Study.arrayProcess(n)) : invoke(() -> Study.linkedProcess(n));
        if (p == null || p.size() != n)
            throw new AssertionError("Последовательность должна содержать N чисел");
        return p;
    }
    static List<Integer> removed(int n, int k) throws Throwable {
        return process(n, k).subList(0, n - 1);
    }
    static int survivor(int n, int k) throws Throwable {
        return process(n, k).get(n - 1);
    }
}
