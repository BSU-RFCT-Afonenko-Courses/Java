import coursesupport.JavaProcess;

/** Два открытых сценария; для запуска достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Последовательность из четырёх чисел";
    private static final String NORMAL_REQUIREMENT = "Максимум и минимум всей последовательности.";
    private static final String NORMAL_INPUT = "stdin: 4 7 -3 0 7; EOF.";
    private static final String NORMAL_EXPECTED = "Максимум: 7\nМинимум: -3";
    private static final String BOUNDARY_NAME = "Нулевое количество";
    private static final String BOUNDARY_REQUIREMENT = "Нулевое количество отклоняется.";
    private static final String BOUNDARY_INPUT = "stdin: 0; EOF.";
    private static final String BOUNDARY_EXPECTED = "Количество должно быть положительным";
    public static JavaProcess.Result observeNormal() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "4 7 -3 0 7");
    }
    public static JavaProcess.Result observeBoundary() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "0");
    }
    public static void checkText(JavaProcess.Result result, String expected) {
        String actual = result.stdout().strip().replaceAll("\\p{javaWhitespace}+", " ");
        if (!actual.equals(expected.replaceAll("\\p{javaWhitespace}+", " ")))
            throw new AssertionError("Проверьте максимум, минимум, их порядок и сообщение при n <= 0.");
    }
    public static void checkNormal(JavaProcess.Result result) { checkText(result, "Максимум: 7 Минимум: -3"); }
    public static void checkBoundary(JavaProcess.Result result) { checkText(result, "Количество должно быть положительным"); }
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
