import java.util.*;
import java.util.function.*;
import java.nio.file.Path;
import java.math.*;
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

    public static Object money(String literal) {
        return new BigDecimal(literal);
    }

    public static Object transform(List<?> prices, Object rate) {
        BigDecimal multiplier = BigDecimal.ONE.add((BigDecimal) rate);
        return prices.stream()
                .map(BigDecimal.class::cast)
                .map(x -> x.multiply(multiplier).setScale(2, RoundingMode.HALF_UP))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<?> values(Object result) {
        return (List<?>) result;
    }

    public static Object representation(Object money) {
        return money;
    }
}
