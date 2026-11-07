import java.lang.FunctionalInterface;
import java.lang.Object;
import java.lang.Throwable;
import java.util.OptionalDouble;
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
    static Object create() throws Throwable {
        return invoke(() -> Study.create());
    }
    static void add(Object s, double v) throws Throwable {
        invoke(() -> {
            Study.add(s, v);
            return null;
        });
    }
    static void remove(Object s, double v) throws Throwable {
        invoke(() -> {
            Study.remove(s, v);
            return null;
        });
    }
    static OptionalDouble nearest(Object s, double q) throws Throwable {
        return invoke(() -> Study.nearest(s, q));
    }
}
