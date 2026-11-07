import java.lang.Throwable;


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
    public static final String NORMAL_NAME = "Два объекта сохраняют свою идентичность";
    public static final String BOUNDARY_NAME = "Две ссылки на один объект";
    public static final String REQUIREMENT =
        "Обмен значений a существующих Test; самопередача сохраняет состояние.";
    public static final String NORMAL_INPUT = "Test(3), Test(8); swap(x,y)";
    public static final String BOUNDARY_INPUT = "Test(-2); swap(x,x)";
    public static final String NORMAL_EXPECTED = "Прежние x/y содержат8/3.";
    public static final String BOUNDARY_EXPECTED = "Прежний x содержит-2.";

    public static Observation observeNormal() throws Throwable {
        Object x = TaskBinding.create(3), y = TaskBinding.create(8);
        Object oldX = x, oldY = y;
        TaskBinding.swap(x, y);
        int a = TaskBinding.value(oldX), b = TaskBinding.value(oldY);
        return new Observation(
                   "Прежние x/y: " + a + ", " + b, a == 8 && b == 3 && x == oldX && y == oldY);
    }
    public static Observation observeBoundary() throws Throwable {
        Object x = TaskBinding.create(-2), old = x;
        TaskBinding.swap(x, x);
        int value = TaskBinding.value(old);
        return new Observation("Прежний объект: a=" + value, value == -2 && x == old);
    }
}
