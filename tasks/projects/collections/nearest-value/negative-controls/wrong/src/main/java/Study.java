import java.util.*;

class Nearby {
    private final java.util.NavigableSet<Double> values = new java.util.TreeSet<>(Double::compare);

    public void add(double v) {
        values.add(v == 0 ? 0.0 : v);
    }

    public void remove(double v) {
        values.remove(v);
    }

    public java.util.OptionalDouble nearest(double query) {
        Double best = null;
        double distance = 0;
        for (double v : values) {
            double d = Math.abs(v - query);
            if (best == null
                    || Double.compare(d, distance) < 0
                    || Double.compare(d, distance) == 0 && Double.compare(v, best) < 0) {
                best = v;
                distance = d;
            }
        }
        return best == null ? java.util.OptionalDouble.empty() : java.util.OptionalDouble.of(best);
    }
}

/** Связь входов с реальной моделью и её операциями. */
public class Study {
    public static Object create() {
        return new Nearby();
    }

    public static void add(Object storage, double value) {
        ((Nearby) storage).add(value);
    }

    public static void remove(Object storage, double value) {
        ((Nearby) storage).remove(value);
    }

    public static OptionalDouble nearest(Object storage, double query) {
        return ((Nearby) storage).nearest(query);
    }
}
