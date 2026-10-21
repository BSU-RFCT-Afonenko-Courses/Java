import coursesupport.JavaProcess;

/** Два открытых сценария; достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Разные длины и повторное вхождение";
    private static final String NORMAL_REQUIREMENT = "Оба экстремума, исходный порядок и повтор слова сохранены.";
    private static final String NORMAL_INPUT = "Аргумент: \"кот, дом и кот!\"; stdin не используется.";
    private static final String NORMAL_EXPECTED = "Максимумы: кот дом кот\\nМинимумы: и; после последней строки LF/CRLF необязателен.";
    private static final String BOUNDARY_NAME = "Все слова равной длины";
    private static final String BOUNDARY_REQUIREMENT = "Оба списка совпадают, повторное вхождение сохранено.";
    private static final String BOUNDARY_INPUT = "Аргумент: \"кот дом кот\"; stdin не используется.";
    private static final String BOUNDARY_EXPECTED = "Максимумы: кот дом кот\\nМинимумы: кот дом кот; после последней строки LF/CRLF необязателен.";
    public static JavaProcess.Result observeNormal() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "", "кот, дом и кот!"); }
    public static JavaProcess.Result observeBoundary() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "", "кот дом кот"); }
    public static void checkText(JavaProcess.Result result, String expected) {
        String actual = result.stdout().replace("\r\n", "\n");
        if (!actual.equals(expected) && !actual.equals(expected + "\n"))
            throw new AssertionError("Проверьте подписи, порядок, повторы и одиночные пробелы. Между списками нужен перевод строки; после последней строки он необязателен.");
    }
    public static void checkNormal(JavaProcess.Result r) { checkText(r, "Максимумы: кот дом кот\nМинимумы: и"); }
    public static void checkBoundary(JavaProcess.Result r) { checkText(r, "Максимумы: кот дом кот\nМинимумы: кот дом кот"); }
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
