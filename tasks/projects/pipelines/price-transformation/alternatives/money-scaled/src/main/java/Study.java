import java.util.*;
import java.math.*;

public class Study {
    private record Amount(long units, int scale) {
        BigDecimal decimal() {
            return BigDecimal.valueOf(units, scale);
        }

        static Amount from(BigDecimal d) {
            return new Amount(d.unscaledValue().longValueExact(), d.scale());
        }
    }

    public static Object money(String s) {
        return Amount.from(new BigDecimal(s));
    }

    public static Object transform(List<?> p, Object r) {
        List<Amount> out = new ArrayList<>();
        for (Object v : p)
            out.add(
                    Amount.from(
                            ((Amount) v)
                                    .decimal()
                                    .multiply(BigDecimal.ONE.add(((Amount) r).decimal()))
                                    .setScale(2, RoundingMode.HALF_UP)));
        return out;
    }

    public static List<?> values(Object r) {
        return (List<?>) r;
    }

    public static Object representation(Object v) {
        Amount a = (Amount) v;
        return new Object[] {a.units(), a.scale()};
    }
}
