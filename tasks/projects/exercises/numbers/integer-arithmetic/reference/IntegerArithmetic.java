public final class IntegerArithmetic {
    private IntegerArithmetic() {}

    public static long sum(int a, int b) {
        return (long) a + b;
    }

    public static double mean(int a, int b) {
        return ((long) a + b) / 2.0;
    }
}
