import coursesupport.JavaProcess;

/** Два открытых сценария; для запуска достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Пробелы до первой точки";
    private static final String NORMAL_REQUIREMENT = "Учитываются U+0020 перед точкой; хвост игнорируется.";
    private static final String NORMAL_INPUT = "stdin: a b c.after dot; EOF без перевода строки.";
    private static final String NORMAL_EXPECTED = "2";
    private static final String BOUNDARY_NAME = "Пустой ввод";
    private static final String BOUNDARY_REQUIREMENT = "EOF до первого символа даёт ноль.";
    private static final String BOUNDARY_INPUT = "Пустой stdin, сразу EOF.";
    private static final String BOUNDARY_EXPECTED = "0";
    public static JavaProcess.Result observeNormal() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "a b c.after dot");
    }
    public static JavaProcess.Result observeBoundary() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "");
    }
    public static void checkCount(JavaProcess.Result result, long expected) {
        String token = result.stdout().strip();
        if (!token.matches("[+-]?[0-9]+") || !new java.math.BigInteger(token).equals(java.math.BigInteger.valueOf(expected)))
            throw new AssertionError("Выведите только целое количество U+0020 до первой точки или EOF.");
    }
    public static void checkNormal(JavaProcess.Result result) { checkCount(result, 2); }
    public static void checkBoundary(JavaProcess.Result result) { checkCount(result, 0); }
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
