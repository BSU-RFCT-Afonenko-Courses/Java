import java.lang.Object;
import java.lang.String;
import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
class TaskBinding {
    static Object value(long n, long d) {
        return Study.create(n, d);
    }
    static Object operation(Object a, Object b, int op) {
        return Study.calculate(a, b, op);
    }
    static long[] parts(Object a) {
        return new long[]{Study.numerator(a), Study.denominator(a)};
    }
    static String text(Object a) {
        return a.toString();
    }
}
