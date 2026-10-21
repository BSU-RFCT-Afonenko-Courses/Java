import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Несколько целых аргументов";
    private static final String NORMAL_WHAT = "Сумма и произведение смешанного набора.";
    private static final String NORMAL_INPUT = "Аргументы 3 -2 4, stdin не используется.";
    private static final String NORMAL_EXPECTED = "Сумма 5, произведение -24; оформление свободно.";
    private static final String BOUNDARY_NAME = "Пустой набор";
    private static final String BOUNDARY_WHAT = "Начальные значения при отсутствии аргументов.";
    private static final String BOUNDARY_INPUT = "Список аргументов пуст.";
    private static final String BOUNDARY_EXPECTED = "Сумма 0, произведение 1.";
    public static JavaProcess.Result run(String... args) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),"",args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("3","-2","4");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run();}
    public static void values(JavaProcess.Result r,long sum,long product){require(java.util.Arrays.equals(PublicBinding.values(r.stdout()),new long[]{sum,product}),"Сопоставьте сумму и произведение с входными аргументами.");}
    public static void checkNormal(JavaProcess.Result r){values(r,5,-24);}
    public static void checkBoundary(JavaProcess.Result r){values(r,0,1);}

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
