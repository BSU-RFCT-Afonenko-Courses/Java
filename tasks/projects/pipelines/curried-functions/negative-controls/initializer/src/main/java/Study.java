import java.util.function.*;

public class Study {
    private interface Addition extends IntFunction<IntUnaryOperator> {}

    private interface TextOuter extends Function<String, Object> {}

    private interface TextPredicate extends Predicate<String> {}

    private interface TextSplit extends Function<String, String[]> {}

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

    public static Object addition() {
        Addition outer = a -> b -> a + b;
        return outer;
    }

    public static Object matcher() {
        TextOuter outer =
                regex -> {
                    TextPredicate inner = text -> text.matches(regex);
                    return inner;
                };
        return outer;
    }

    public static Object splitter() {
        TextOuter outer =
                regex -> {
                    TextSplit inner = text -> text.split(regex, -1);
                    return inner;
                };
        return outer;
    }

    public static Object bindAddition(Object outer, int first) {
        return Addition.class.cast(outer).apply(first);
    }

    public static Object bindText(Object outer, String regex) {
        return TextOuter.class.cast(outer).apply(regex);
    }

    public static int sum(Object bound, int second) {
        return ((IntUnaryOperator) bound).applyAsInt(second);
    }

    public static boolean matches(Object bound, String text) {
        return TextPredicate.class.cast(bound).test(text);
    }

    public static String[] split(Object bound, String text) {
        return TextSplit.class.cast(bound).apply(text);
    }
}
