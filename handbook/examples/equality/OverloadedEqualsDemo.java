public class OverloadedEqualsDemo {
    private static final class OverloadedId {
        private final String value;

        private OverloadedId(String value) {
            this.value = value;
        }

        // Намеренная ошибка: этот метод перегружает имя equals.
        public boolean equals(OverloadedId other) {
            return other != null && value.equals(other.value);
        }
    }

    public static void main(String[] args) {
        var first = new OverloadedId("s17");
        var second = new OverloadedId("s17");
        Object argument = second;
        System.out.println(first.equals(second));
        System.out.println(first.equals(argument));
    }
}
