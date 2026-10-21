import coursesupport.JavaProcess;

/** Два открытых сценария; для запуска достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Имя из аргумента";
    private static final String NORMAL_REQUIREMENT = "Первый аргумент сохраняется в приветствии.";
    private static final String NORMAL_INPUT = "Аргумент: Анна; stdin пуст.";
    private static final String NORMAL_EXPECTED = "Здравствуйте, Анна!";
    private static final String BOUNDARY_NAME = "Аргументы отсутствуют";
    private static final String BOUNDARY_REQUIREMENT = "Отсутствие имени обрабатывается отдельно.";
    private static final String BOUNDARY_INPUT = "Без аргументов; stdin пуст.";
    private static final String BOUNDARY_EXPECTED = "Укажите имя.";
    public static JavaProcess.Result observeNormal() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "", "Анна");
    }
    public static JavaProcess.Result observeBoundary() throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class, "");
    }
    public static void checkLine(JavaProcess.Result result, String expected) {
        String text = result.stdout().replace("\r\n", "\n").replace("\r", "\n");
        if (!text.equals(expected) && !text.equals(expected + "\n"))
            throw new AssertionError("Проверьте точный текст и сохранность имени в сообщении.");
    }
    public static void checkNormal(JavaProcess.Result result) { checkLine(result, "Здравствуйте, Анна!"); }
    public static void checkBoundary(JavaProcess.Result result) { checkLine(result, "Укажите имя."); }
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
