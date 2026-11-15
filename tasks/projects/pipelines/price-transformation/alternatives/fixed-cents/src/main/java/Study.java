import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Study {
    private record Cents(long units) {
        BigDecimal decimal() {
            return BigDecimal.valueOf(units, 2);
        }

        static Cents from(BigDecimal value) {
            return new Cents(
                    value.setScale(2, RoundingMode.HALF_EVEN).unscaledValue().longValueExact());
        }
    }

    public static Object money(String s) {
        return Cents.from(new BigDecimal(s));
    }

    public static Object transform(List<?> input, Object rate) {
        List<Cents> result = new ArrayList<>();
        for (Object v : input)
            result.add(
                    Cents.from(
                            ((Cents) v)
                                    .decimal()
                                    .multiply(BigDecimal.ONE.add(((Cents) rate).decimal()))));
        return java.util.Collections.unmodifiableList(result);
    }

    public static List<?> values(Object r) {
        return (List<?>) r;
    }

    public static Object representation(Object v) {
        return new Object[] {((Cents) v).units(), 2};
    }
}
