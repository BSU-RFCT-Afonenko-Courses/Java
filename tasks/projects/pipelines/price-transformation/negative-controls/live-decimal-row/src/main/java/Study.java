import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Study {
    static final class Mutable extends BigDecimal {
        BigDecimal value;

        Mutable(String s) {
            super(s);
            value = new BigDecimal(s);
        }

        @Override
        public BigInteger unscaledValue() {
            return value.unscaledValue();
        }

        @Override
        public int scale() {
            return value.scale();
        }

        @Override
        public BigDecimal multiply(BigDecimal ignored) {
            return this;
        }

        @Override
        public BigDecimal setScale(int scale, RoundingMode mode) {
            return this;
        }

        @Override
        public int compareTo(BigDecimal other) {
            return value.compareTo(other instanceof Mutable m ? m.value : other);
        }
    }

    public static Object money(String s) {
        return new Mutable(s);
    }

    public static Object representation(Object v) {
        return v;
    }

    public static Object transform(List<?> input, Object rate) {
        List<Mutable> out = new ArrayList<>();
        BigDecimal multiplier = BigDecimal.ONE.add(((Mutable) rate).value);
        for (Object v : input) {
            Mutable a = (Mutable) v;
            a.value = a.value.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
            out.add(a);
        }
        return out;
    }

    public static List<?> values(Object r) {
        return (List<?>) r;
    }
}
