import java.lang.AssertionError;
import java.lang.String;
import java.util.Arrays;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Два опубликованных наблюдения; внутренние имена решения свободны. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
    public static final String NORMAL_NAME="Сумма и сохранённый массив";
    public static final String BOUNDARY_NAME="Вызов без аргументов";
    public static final String REQUIREMENT="int сумма, пустой вызов0, переданный массив сохраняется.";
    public static final String NORMAL_INPUT="sum([3,-2,4])";
    public static final String BOUNDARY_INPUT="sum()";
    public static final String NORMAL_EXPECTED="Возврат5, тот же массив [3,-2,4].";
    public static final String BOUNDARY_EXPECTED="Возврат0.";

    public static Observation observeNormal() {
        int[] input={3,-2,4};int result=TaskBinding.sum(input);
        return new Observation("Возвращено " + result + "; массив " + Arrays.toString(input), result==5 && Arrays.equals(input,new int[]{3,-2,4}));
    }
    public static Observation observeBoundary() {
        int result=TaskBinding.sum();return new Observation("Возвращено " + result,result==0);
    }


}
