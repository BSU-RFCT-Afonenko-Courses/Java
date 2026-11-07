import java.lang.Object;
import java.lang.String;
import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
class TaskBinding {
    static Object animal(int k, String n) {
        return Study.create(k, n);
    }
    static String voice(Object a) {
        return Study.voice(a);
    }
    static String[] actions(Object a) {
        return new String[]{Study.action(a, 0), Study.action(a, 1), Study.action(a, 2)};
    }
    static String[] dogActions(Object a) {
        return new String[]{Study.action(a, 0), Study.action(a, 1)};
    }
    static String name(Object a) {
        return Study.name(a);
    }
    static boolean voiceDescribes(int k, String n, String s) {
        return s != null && s.contains(n) && s.contains(new String[]{"Animal", "Dog", "Puppy"}[k]);
    }
    static boolean actionDescribes(int k, String n, String s) {
        return s != null && s.contains(n) && !s.isBlank();
    }
}
