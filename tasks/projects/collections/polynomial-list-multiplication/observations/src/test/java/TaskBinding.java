import java.lang.Double;
import java.lang.FunctionalInterface;
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
    static List<Double> multiply(List<Double> a, List<Double> b) throws Throwable {
        return invoke(() -> Study.multiply(a, b));
    }
}
