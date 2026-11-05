import java.lang.Object;
import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
class TaskBinding {
    static Object oldQueue() {
        return Study.oldQueue();
    }
    static Object newQueue() {
        return Study.newQueue();
    }
    static void put(Object q, char c) {
        Study.put(q, c);
    }
    static char get(Object q) {
        return Study.get(q);
    }
    static void reset(Object q) {
        Study.reset(q);
    }
}
