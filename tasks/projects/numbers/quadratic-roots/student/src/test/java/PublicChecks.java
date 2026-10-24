import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Два различных корня";
    private static final String NORMAL_WHAT = "Положительный дискриминант.";
    private static final String NORMAL_INPUT = "Аргументы 1 -3 2.";
    private static final String NORMAL_EXPECTED = "Два корня 1 и 2 в любом порядке; допуск 1e-8+1e-12*abs(x).";
    private static final String BOUNDARY_NAME = "Нулевой дискриминант";
    private static final String BOUNDARY_WHAT = "Граница количества действительных корней.";
    private static final String BOUNDARY_INPUT = "Аргументы 1 -2 1.";
    private static final String BOUNDARY_EXPECTED = "Один корень 1 с тем же смешанным допуском.";
    public static JavaProcess.Result run(String... args) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),"",args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("1","-3","2");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run("1","-2","1");}
    public static void roots(JavaProcess.Result r,double... expected){double[] actual=PublicBinding.roots(r.stdout());require(actual.length==expected.length,"Проверьте количество действительных корней по знаку дискриминанта.");java.util.Arrays.sort(actual);double[] sorted=expected.clone();java.util.Arrays.sort(sorted);for(int i=0;i<sorted.length;i++)require(Double.isFinite(actual[i])&&Math.abs(actual[i]-sorted[i])<=1e-8+1e-12*Math.abs(sorted[i]),"Проверьте точность корней; порядок двух значений свободен.");}
    public static void checkNormal(JavaProcess.Result r){roots(r,1,2);}
    public static void checkBoundary(JavaProcess.Result r){roots(r,1);}

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
