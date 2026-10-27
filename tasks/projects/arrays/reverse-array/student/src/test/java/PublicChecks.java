import coursesupport.JavaProcess;

/** Два открытых сценария; достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Обратный порядок с повторением";
    private static final String NORMAL_REQUIREMENT = "Обратный порядок и сохранение повторного значения.";
    private static final String NORMAL_INPUT = "Без аргументов; stdin: 4 7 -2 7 0; EOF.";
    private static final String NORMAL_EXPECTED = "0 7 -2 7";
    private static final String BOUNDARY_NAME = "Пустой массив";
    private static final String BOUNDARY_REQUIREMENT = "Массив длины ноль не даёт элементов вывода.";
    private static final String BOUNDARY_INPUT = "Без аргументов; stdin: 0; EOF.";
    private static final String BOUNDARY_EXPECTED = "Нет чисел и другого текста; допустимо пробельное оформление.";
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
