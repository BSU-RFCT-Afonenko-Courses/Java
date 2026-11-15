import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
    interface Quadratic {
        double value(double a, double b, double c);
    }

    public static Object function() {
        Quadratic q = (a, b, c) -> b * b + 4 * a * c;
        return q;
    }

    public static double apply(Object function, double a, double b, double c) {
        return ((Quadratic) function).value(a, b, c);
    }
}
