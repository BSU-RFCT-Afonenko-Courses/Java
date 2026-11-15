import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.util.stream.*;

public class Study {
    static {
        if (java.lang.System.getProperty("course.initializer.marker") != null) {
            try {
                java.nio.file.Files.writeString(
                        java.nio.file.Path.of(
                                java.lang.System.getProperty("course.initializer.marker")),
                        "student initializer executed");
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
            throw new java.lang.IllegalStateException("initializer sentinel");
        }
    }

    record Point(int x, int y) {
        int square() {
            return x * x + y * y;
        }

        public String toString() {
            return "(" + x + ":" + y + ")";
        }
    }

    public static Object point(int x, int y) {
        return new Point(x, y);
    }

    public static int x(Object p) {
        return ((Point) p).x();
    }

    public static int y(Object p) {
        return ((Point) p).y();
    }

    public static void printOrdered(List<?> points) {
        System.out.print(
                points.stream()
                        .map(Point.class::cast)
                        .sorted((a, b) -> Integer.compare(a.square(), b.square()))
                        .map(Point::toString)
                        .collect(Collectors.joining(" ")));
    }
}
