import coursesupport.JavaProcess;

/** Неизменённые наблюдения и сравнения normal/boundary исходного PublicChecks. */
public class PublicChecks {
    public static JavaProcess.Result observeNormal() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "4 7 -2 7 0"); }
    public static JavaProcess.Result observeBoundary() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "0"); }
    public static void checkValues(JavaProcess.Result result, double... expected) {
        double[] actual = coursesupport.OutputNumbers.finiteNumbers(result.stdout());
        if (actual.length != expected.length) throw new AssertionError("Проверьте число выведенных элементов, включая пустой массив.");
        for (int i = 0; i < expected.length; i++) if (Math.abs(actual[i] - expected[i]) > 1e-8)
            throw new AssertionError("Выведите элементы от последнего к первому, сохранив повторения.");
    }
    public static void checkNormal(JavaProcess.Result r) { checkValues(r, 0, 7, -2, 7); }
    public static void checkBoundary(JavaProcess.Result r) { checkValues(r); }
}
