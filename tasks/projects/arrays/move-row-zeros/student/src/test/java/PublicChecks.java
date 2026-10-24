import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Обязательная матрица при пустом вводе";
    private static final String NORMAL_WHAT = "Опубликованный пример и устойчивый порядок ненулевых.";
    private static final String NORMAL_INPUT = "stdin пуст и завершён EOF.";
    private static final String NORMAL_EXPECTED = "Строки -3 3 0 / 2 -1 0 / 1 3 -2, по три целых числа.";
    private static final String BOUNDARY_NAME = "Три состояния строки";
    private static final String BOUNDARY_WHAT = "Нули в начале, отсутствие нулей, полностью нулевая строка.";
    private static final String BOUNDARY_INPUT = "stdin 0 0 -3 / 3 -1 2 / 0 0 0, затем EOF.";
    private static final String BOUNDARY_EXPECTED = "Строки -3 0 0 / 3 -1 2 / 0 0 0.";
    public static coursesupport.JavaProcess.Result run(String input) throws Exception {return coursesupport.JavaProcess.runCheckingUncaught(Main.class,input);}
    static String[] tokens(String text){return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");}
    static void rows(int[][] expected,String output){String[] lines=output.strip().split("\\R");require(lines.length==3,"Выведите три строки матрицы.");for(int i=0;i<3;i++){String[] v=tokens(lines[i]);require(v.length==3,"Выведите три целых значения в каждой строке.");try{for(int j=0;j<3;j++)require(new java.math.BigInteger(v[j]).equals(java.math.BigInteger.valueOf(expected[i][j])),"Сохраните порядок ненулевых и количество нулей в каждой строке.");}catch(NumberFormatException e){throw new AssertionError("Выведите целые числа без подписей.");}}}
    public static JavaProcess.Result observeNormal()throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run("0 0 -3\n3 -1 2\n0 0 0\n");}
    public static void checkNormal(JavaProcess.Result r){rows(new int[][]{{-3,3,0},{2,-1,0},{1,3,-2}},r.stdout());}
    public static void checkBoundary(JavaProcess.Result r){rows(new int[][]{{-3,0,0},{3,-1,2},{0,0,0}},r.stdout());}
    private static String shown(String text) {
        if(text.isEmpty()) return "Пустой стандартный вывод.";
        return "\""+text.replace("\\","\\\\").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t").replace("\f","\\f")+"\"";
    }
    private static int example(boolean normal) {
        System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));
        System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));
        System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));
        System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
        try {
            var observed=normal?observeNormal():observeBoundary();
            System.out.println("Получено: "+shown(observed.stdout()));
            if(normal)checkNormal(observed);else checkBoundary(observed);
            System.out.println("Статус: наблюдаемый результат соответствует.");return 0;
        } catch(JavaProcess.StudentTimeout failure) {
            System.out.println("Статус: превышено время; проверьте завершение программы на указанном входе.");return 1;
        } catch(JavaProcess.StudentOutputLimit failure) {
            System.out.println("Статус: превышен объём вывода; проверьте повторяющуюся печать.");return 1;
        } catch(JavaProcess.UncaughtStudentException failure) {
            System.out.println("Статус: ошибка выполнения; программа завершилась необработанным исключением.");return 1;
        } catch(AssertionError failure) {
            System.out.println("Статус: неверный результат. "+failure.getMessage());return 1;
        } catch(Exception|LinkageError failure) {
            System.out.println("Статус: сбой подготовки проверки; требуется проверить привязку и среду JDK.");return 2;
        }
    }
    public static void main(String[] args) {int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
    static void require(boolean condition,String message){if(!condition)throw new AssertionError(message);}
}
