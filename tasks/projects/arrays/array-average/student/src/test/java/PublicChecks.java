import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Дробное среднее десяти чисел";
    private static final String NORMAL_WHAT = "Участие десяти значений в среднем.";
    private static final String NORMAL_INPUT = "stdin 1 2 3 4 5 6 7 8 9 10, затем EOF.";
    private static final String NORMAL_EXPECTED = "Одно число 5.5; абсолютный допуск 1e-8.";
    private static final String BOUNDARY_NAME = "Нижняя граница значений";
    private static final String BOUNDARY_WHAT = "Отрицательное среднее на разрешённой границе.";
    private static final String BOUNDARY_INPUT = "stdin: десять значений -1000000, затем EOF.";
    private static final String BOUNDARY_EXPECTED = "Одно число -1000000; абсолютный допуск 1e-8.";
    public static coursesupport.JavaProcess.Result run(String input) throws Exception {return coursesupport.JavaProcess.runCheckingUncaught(Main.class,input);}
    static String[] tokens(String text){return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");}
    static void mean(String expected,String output){String[] values=tokens(output);require(values.length==1,"Выведите только одно среднее.");try{require(values[0].matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"),"Используйте десятичную или экспоненциальную запись с точкой.");var delta=new java.math.BigDecimal(values[0]).subtract(new java.math.BigDecimal(expected)).abs();require(delta.compareTo(new java.math.BigDecimal("0.00000001"))<=0,"Сопоставьте математическое среднее и абсолютный допуск 1e-8.");}catch(NumberFormatException e){throw new AssertionError("Выведите конечное числовое среднее.");}}
    public static JavaProcess.Result observeNormal()throws Exception{return run("1 2 3 4 5 6 7 8 9 10");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run("-1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000 -1000000");}
    public static void checkNormal(JavaProcess.Result r){mean("5.5",r.stdout());}
    public static void checkBoundary(JavaProcess.Result r){mean("-1000000",r.stdout());}
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
