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
    static Object create(int a) throws Throwable {
        return invoke(()->Study.create(a));
    }
    static int value(Object x) throws Throwable {
        return invoke(()->Study.value(x));
    }
    static void swap(Object a, Object b) throws Throwable {
        invoke(()->{
            Study.swap(a, b);
            return null;
        });
    }
}
