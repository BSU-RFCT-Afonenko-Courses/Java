import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Дробная исходная сила";
    private static final String NORMAL_WHAT = "Вычисление 17% с дробной частью.";
    private static final String NORMAL_INPUT = "stdin 7.5, затем EOF; аргументов нет.";
    private static final String NORMAL_EXPECTED = "Одно конечное число 1.275 с опубликованным max-допуском.";
    private static final String BOUNDARY_NAME = "Нулевая сила";
    private static final String BOUNDARY_WHAT = "Нижняя граница неотрицательного входа.";
    private static final String BOUNDARY_INPUT = "stdin 0, затем EOF.";
    private static final String BOUNDARY_EXPECTED = "Одно конечное число с абсолютной ошибкой не более 1e-8 от нуля.";
    public static JavaProcess.Result run(String input,String...args) throws Exception {return JavaProcess.runCheckingUncaught(Main.class,input,args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("7.5");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run("0");}
    public static void weight(String input,String output){
        String value=output.strip();
        require(value.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)(?:[eE][+-]?[0-9]+)?"),"Выведите одно число в десятичной или экспоненциальной форме с точкой.");
        double[] numbers=coursesupport.OutputNumbers.finiteNumbers(value);
        require(numbers.length==1,"Нужно одно конечное число.");
        var expected=new java.math.BigDecimal(Double.parseDouble(input)).multiply(new java.math.BigDecimal("0.17"));
        var tolerance=new java.math.BigDecimal("1e-8").max(expected.abs().multiply(new java.math.BigDecimal("1e-12")));
        require(new java.math.BigDecimal(value).subtract(expected).abs().compareTo(tolerance)<=0,"Проверьте 17% и опубликованный абсолютный/относительный допуск.");
    }
    public static void checkNormal(JavaProcess.Result r){weight("7.5",r.stdout());}
    public static void checkBoundary(JavaProcess.Result r){weight("0",r.stdout());}

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
