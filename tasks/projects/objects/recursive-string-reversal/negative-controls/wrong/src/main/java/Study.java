import java.util.*;

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static void printReversed(String text) {
        Reverse.print(text);
    }
}

class Main {
    public static void main(String[] args) {
        String text = args.length == 0 ? "Java 25!" : args[0];
        Reverse.print(text);
    }
}

class Reverse {
    public static void print(String s) {
        print(s, s.length() - 1);
    }

    private static void print(String s, int i) {
        if (i < 0) return;
        System.out.print(s.charAt(s.length() - 1 - i));
        print(s, i - 1);
    }
}
