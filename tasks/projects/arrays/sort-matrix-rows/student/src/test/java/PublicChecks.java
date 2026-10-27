import coursesupport.JavaProcess;

/** Два открытых сценария; достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Переставленные строки обязательного примера";
    private static final String NORMAL_REQUIREMENT = "Строки упорядочены по сумме целиком.";
    private static final String NORMAL_INPUT = "Без аргументов; stdin: 1 3 -2\\n-3 0 3\\n2 -1 0; EOF.";
    private static final String NORMAL_EXPECTED = "Три строки: -3 0 3; 2 -1 0; 1 3 -2.";
    private static final String BOUNDARY_NAME = "Разные строки с равной суммой";
    private static final String BOUNDARY_REQUIREMENT = "Разные строки с равной суммой сохраняют взаимный порядок.";
    private static final String BOUNDARY_INPUT = "Без аргументов; stdin: 2 -1 0\\n0 1 0\\n0 0 0; EOF.";
    private static final String BOUNDARY_EXPECTED = "Три строки: 0 0 0; 2 -1 0; 0 1 0.";
    public static JavaProcess.Result observeNormal() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "1 3 -2\n-3 0 3\n2 -1 0"); }
    public static JavaProcess.Result observeBoundary() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "2 -1 0\n0 1 0\n0 0 0"); }
    public static void checkMatrix(JavaProcess.Result result, double[][] expected) {
        double[][] actual = coursesupport.OutputNumbers.finiteMatrix(result.stdout(), 3, 3);
        for (int row = 0; row < 3; row++) for (int column = 0; column < 3; column++)
            if (Math.abs(actual[row][column] - expected[row][column]) > 1e-8)
                throw new AssertionError("Переставляйте целые строки по сумме и сохраняйте порядок строк с равными суммами.");
    }
    public static void checkNormal(JavaProcess.Result r) { checkMatrix(r, new double[][]{{-3,0,3},{2,-1,0},{1,3,-2}}); }
    public static void checkBoundary(JavaProcess.Result r) { checkMatrix(r, new double[][]{{0,0,0},{2,-1,0},{0,1,0}}); }
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
