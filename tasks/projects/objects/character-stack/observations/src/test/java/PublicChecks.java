import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.AssertionError;
import java.lang.Object;
import java.lang.Runnable;
import java.lang.String;
import java.lang.System;
import java.util.Arrays;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Два опубликованных наблюдения; внутренние имена решения свободны. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
    public static final String NORMAL_NAME="Последний символ извлекается первым";
    public static final String BOUNDARY_NAME="Ёмкость один и сохранение после отказов";
    public static final String REQUIREMENT="LIFO; точные сообщения отказов, успешные операции молчат, отказ сохраняет состояние.";
    public static final String NORMAL_INPUT="Stack(2): push A/B, pop/pop";
    public static final String BOUNDARY_INPUT="Stack(1): pop, push X/Y, pop/pop, push Z, pop";
    public static final String NORMAL_EXPECTED="Возвращены B,A; сообщений нет.";
    public static final String BOUNDARY_EXPECTED="Возвраты0,X,0,Z; строки: Стек пуст; Стек переполнен; Стек пуст.";

    private static String capture(Runnable action) {
        PrintStream original=System.out;ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        try(PrintStream out=new PrintStream(bytes,true,StandardCharsets.UTF_8)) {
            System.setOut(out);action.run();
        } finally {System.setOut(original);}
        return bytes.toString(StandardCharsets.UTF_8).replace("\r\n","\n");
    }
    public static Observation observeNormal() {
        Object stack=TaskBinding.create(2);char[] returned=new char[2];
        String text=capture(()->{TaskBinding.push(stack,'A');TaskBinding.push(stack,'B');returned[0]=TaskBinding.pop(stack);returned[1]=TaskBinding.pop(stack);});
        return new Observation("Возвраты "+Arrays.toString(returned)+"; сообщения="+text.replace("\n","\\n"),Arrays.equals(returned,new char[]{'B','A'}) && text.isEmpty());
    }
    public static Observation observeBoundary() {
        Object stack=TaskBinding.create(1);char[] returned=new char[4];
        String text=capture(()->{returned[0]=TaskBinding.pop(stack);TaskBinding.push(stack,'X');TaskBinding.push(stack,'Y');returned[1]=TaskBinding.pop(stack);returned[2]=TaskBinding.pop(stack);TaskBinding.push(stack,'Z');returned[3]=TaskBinding.pop(stack);});
        int[] codes={returned[0],returned[1],returned[2],returned[3]};
        return new Observation("Коды char "+Arrays.toString(codes)+"; сообщения="+text.replace("\n","\\n"),Arrays.equals(returned,new char[]{'\0','X','\0','Z'}) && text.equals("Стек пуст\nСтек переполнен\nСтек пуст\n"));
    }


}
