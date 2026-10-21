import coursesupport.JavaProcess;

/** Два открытых сценария; достаточно JDK 25. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Обязательная матрица с дробными средними";
    private static final String NORMAL_REQUIREMENT = "При пустом stdin выбран обязательный пример; вычитается среднее каждой строки.";
    private static final String NORMAL_INPUT = "Без аргументов; пустой stdin, сразу EOF.";
    private static final String NORMAL_EXPECTED = "Три строки: -3 0 3; 5/3 -4/3 -1/3; 1/3 7/3 -8/3, допуск 1e-8.";
    private static final String BOUNDARY_NAME = "Постоянные строки и крайние значения";
    private static final String BOUNDARY_REQUIREMENT = "Вычитание среднего постоянной строки даёт нулевую строку.";
    private static final String BOUNDARY_INPUT = "Без аргументов; stdin: 3 3 3\\n-3 -3 -3\\n0 0 0; EOF.";
    private static final String BOUNDARY_EXPECTED = "Три строки по три нуля.";
    public static JavaProcess.Result observeNormal() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, ""); }
    public static JavaProcess.Result observeBoundary() throws Exception { return JavaProcess.runCheckingUncaught(Main.class, "3 3 3\n-3 -3 -3\n0 0 0"); }
    public static void checkMatrix(JavaProcess.Result result, double[][] expected) {
        double[][] actual = coursesupport.OutputNumbers.finiteMatrix(result.stdout(), 3, 3);
        for (int row = 0; row < 3; row++) for (int column = 0; column < 3; column++)
            if (Math.abs(actual[row][column] - expected[row][column]) > 1e-8)
                throw new AssertionError("Вычтите среднее своей строки; сохраните дробную часть с абсолютной ошибкой не более 1e-8.");
    }
    public static void checkNormal(JavaProcess.Result r) { checkMatrix(r, new double[][]{{-3,0,3},{5.0/3,-4.0/3,-1.0/3},{1.0/3,7.0/3,-8.0/3}}); }
    public static void checkBoundary(JavaProcess.Result r) { checkMatrix(r, new double[3][3]); }
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
