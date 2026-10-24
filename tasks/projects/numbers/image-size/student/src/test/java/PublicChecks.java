import coursesupport.JavaProcess;

/** Два открытых сценария; для запуска достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Изображение размером четыре МиБ";
    private static final String NORMAL_REQUIREMENT = "Количество байтов и перевод в мебибайты.";
    private static final String NORMAL_INPUT = "stdin: 1024 1024 4; EOF.";
    private static final String NORMAL_EXPECTED = "4194304 4.0";
    private static final String BOUNDARY_NAME = "Один однобайтовый пиксель";
    private static final String BOUNDARY_REQUIREMENT = "Минимальные размеры и дробная часть МиБ.";
    private static final String BOUNDARY_INPUT = "stdin: 1 1 1; EOF.";
    private static final String BOUNDARY_EXPECTED = "1 0.00000095367431640625";
    public static JavaProcess.Result observeNormal() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "1024 1024 4");
    }
    public static JavaProcess.Result observeBoundary() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "1 1 1");
    }
    public static void checkImage(JavaProcess.Result result, long bytes, double mib) {
        String[] tokens = result.stdout().strip().split("\\p{javaWhitespace}+");
        if (tokens.length != 2 || !tokens[0].matches("[+-]?[0-9]+"))
            throw new AssertionError("Выведите целое количество байтов и размер в МиБ без подписей.");
        try {
            if (!new java.math.BigInteger(tokens[0]).equals(java.math.BigInteger.valueOf(bytes)))
                throw new AssertionError("Проверьте точность промежуточного произведения.");
        } catch (NumberFormatException failure) {
            throw new AssertionError("Количество байтов должно быть целым.");
        }
        double[] numbers = coursesupport.OutputNumbers.finiteNumbers(result.stdout());
        if (Math.abs(numbers[1] - mib) > 1e-8)
            throw new AssertionError("Проверьте деление на 1048576 и абсолютную ошибку не более 1e-8.");
    }
    public static void checkNormal(JavaProcess.Result result) { checkImage(result, 4194304, 4.0); }
    public static void checkBoundary(JavaProcess.Result result) { checkImage(result, 1, 0.00000095367431640625); }
    public static void main(String[] args) {
        int failed = 0;
        for (int index = 0; index < 2; index++) {
            boolean normal = index == 0;
            System.out.println("Сценарий: " + (normal ? NORMAL_NAME : BOUNDARY_NAME));
            System.out.println("Что проверяется: " + (normal ? NORMAL_REQUIREMENT : BOUNDARY_REQUIREMENT));
            System.out.println("Вход и действие: " + (normal ? NORMAL_INPUT : BOUNDARY_INPUT));
            System.out.println("Ожидаемый результат: " + (normal ? NORMAL_EXPECTED : BOUNDARY_EXPECTED));
            try {
                JavaProcess.Result result = normal ? observeNormal() : observeBoundary();
                System.out.println("Получено: " + result.stdout().replace("\r", "\\r").replace("\n", "\\n"));
                if (normal) checkNormal(result); else checkBoundary(result);
                System.out.println("Статус: пройдено");
            } catch (JavaProcess.StudentTimeout failure) {
                failed++;
                System.out.println("Получено: программа не завершилась за время проверки.");
                System.out.println("Статус: превышено время; программа должна завершиться на указанном входе.");
            } catch (JavaProcess.StudentOutputLimit failure) {
                failed++;
                System.out.println("Получено: программа превысила допустимый объём вывода.");
                System.out.println("Статус: превышен объём вывода; проверьте завершение обработки и повторяющуюся печать.");
            } catch (JavaProcess.UncaughtStudentException failure) {
                failed++;
                System.out.println("Получено: программа завершилась необработанным исключением.");
                System.out.println("Статус: ошибка выполнения; проверьте обработку указанного входа.");
            } catch (AssertionError failure) {
                failed++;
                System.out.println("Статус: неверный результат. " + failure.getMessage());
            } catch (Exception | LinkageError failure) {
                failed++;
                System.out.println("Статус: сбой проверяющей инфраструктуры; повторите подготовку JDK-проверки.");
            }
        }
        if (failed != 0) System.exit(1);
    }
}
