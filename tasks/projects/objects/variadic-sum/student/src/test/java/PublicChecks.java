import java.util.Arrays;

/** Два открытых сценария; TaskBinding связывает наблюдения с решением. */
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

    public static Observation observeNormal() {
        int[] input = {3, -2, 4};
        int result = TaskBinding.sum(input);
        return new Observation("Возвращено " + result + "; массив " + Arrays.toString(input),
                               result == 5 && Arrays.equals(input, new int[] {3, -2, 4}));
    }
    public static Observation observeBoundary() {
        int result = TaskBinding.sum();
        return new Observation("Возвращено " + result, result == 0);
    }

    public static void main(String[] args) {
        boolean success = true;
        for (String id : new String[] {"normal", "boundary"}) {
            System.out.println("Проверка: " + (id.equals("normal") ? NORMAL_NAME : BOUNDARY_NAME));
            System.out.println("Требование: " + REQUIREMENT);
            System.out.println(
                "Вход/действие: " + (id.equals("normal") ? NORMAL_INPUT : BOUNDARY_INPUT));
            System.out.println(
                "Ожидается: " + (id.equals("normal") ? NORMAL_EXPECTED : BOUNDARY_EXPECTED));
            Observation result;
            try {
                result = id.equals("normal") ? observeNormal() : observeBoundary();
            } catch (Throwable error) {
                System.out.println("Получено: выполнение операции завершилось исключением "
                                   + error.getClass().getSimpleName());
                System.out.println("Статус: ошибка выполнения. Проверьте действие и согласование "
                                   + "клиента с API решения.");
                success = false;
                continue;
            }
            System.out.println("Получено: " + result.actual());
            System.out.println("Статус: "
                               + (result.passed() ? "совпадает с открытым ожиданием" :
                                  "требует исправления"));
            success &= result.passed();
        }
        if (!success)
            System.exit(1);
    }
}
