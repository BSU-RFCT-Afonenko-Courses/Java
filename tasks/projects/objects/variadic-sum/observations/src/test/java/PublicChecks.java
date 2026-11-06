import java.lang.Throwable;
import java.util.Arrays;

/** Два опубликованных наблюдения; внутренние имена решения свободны. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) {
        if (!result.passed())
            throw new AssertionError("Сценарий: " + NORMAL_NAME + "\nВход/действие: " + NORMAL_INPUT
                                     + "\nОжидается: " + NORMAL_EXPECTED + "\nПолучено: " + result.actual());
    }
    public static void checkBoundary(Observation result) {
        if (!result.passed())
            throw new AssertionError("Сценарий: " + BOUNDARY_NAME
                                     + "\nВход/действие: " + BOUNDARY_INPUT + "\nОжидается: " + BOUNDARY_EXPECTED
                                     + "\nПолучено: " + result.actual());
    }
    public static final String NORMAL_NAME = "Сумма и сохранённый массив";
    public static final String BOUNDARY_NAME = "Вызов без аргументов";
    public static final String REQUIREMENT =
        "int сумма, пустой вызов0, переданный массив сохраняется.";
    public static final String NORMAL_INPUT = "sum([3,-2,4])";
    public static final String BOUNDARY_INPUT = "sum()";
    public static final String NORMAL_EXPECTED = "Возврат5, тот же массив [3,-2,4].";
    public static final String BOUNDARY_EXPECTED = "Возврат0.";

    public static Observation observeNormal() throws Throwable {
        int[] input = {3, -2, 4};
        int result = TaskBinding.sum(input);
        return new Observation("Возвращено " + result + "; массив " + Arrays.toString(input),
                               result == 5 && Arrays.equals(input, new int[] {3, -2, 4}));
    }
    public static Observation observeBoundary() throws Throwable {
        int result = TaskBinding.sum();
        return new Observation("Возвращено " + result, result == 0);
    }
}
