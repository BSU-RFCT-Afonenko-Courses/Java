import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.lang.System;
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
    static String printed(String s) throws Throwable {
        PrintStream old = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try
            (PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
                System.setOut(out);
                invoke(()->{
                    Study.printReversed(s);
                    return null;
                });
            }
        finally { System.setOut(old); }
        return bytes.toString(StandardCharsets.UTF_8);
    }
}
