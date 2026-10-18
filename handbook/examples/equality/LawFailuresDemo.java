import java.util.Objects;

public class LawFailuresDemo {
    private static final class LooseId {
        private final String value;

        private LooseId(String value) {
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof LooseId id) return value.equals(id.value);
            // Намеренное нарушение симметрии при сравнении со String.
            if (other instanceof String text) return value.equals(text);
            return false;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    private static boolean near(int first, int second) {
        return Math.abs((long) first - second) <= 1;
    }

    public static void main(String[] args) {
        var proper = new StudentId("s17");
        System.out.println(proper.equals(new StudentId("s17")));
        System.out.println(proper.equals("s17"));
        System.out.println(proper.equals(null));

        var loose = new LooseId("s17");
        System.out.println(loose.equals("s17"));
        System.out.println("s17".equals(loose));

        System.out.println(near(80, 81));
        System.out.println(near(81, 82));
        System.out.println(near(80, 82));
    }
}
