import java.lang.FunctionalInterface;
import java.lang.Object;
import java.lang.Throwable;
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
    static Object create(int n) throws Throwable {
        return invoke(()->Study.create(n));
    }
    static void push(Object q, char c) throws Throwable {
        invoke(()->{
            Study.push(q, c);
            return null;
        });
    }
    static char pop(Object q) throws Throwable {
        return invoke(()->Study.pop(q));
    }
}
