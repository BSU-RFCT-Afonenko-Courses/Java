import java.lang.Double;
import java.lang.FunctionalInterface;
import java.lang.Integer;
import java.lang.Throwable;
import java.util.HashMap;
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
    static HashMap<Integer, Double> add(HashMap<Integer, Double> a, HashMap<Integer, Double> b) throws Throwable {
        return invoke(() -> Study.add(a, b));
    }
}
