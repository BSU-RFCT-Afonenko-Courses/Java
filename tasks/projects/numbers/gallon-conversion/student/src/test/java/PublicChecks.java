import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Дробный результат преобразования";
    private static final String NORMAL_WHAT = "Фиксированный объём с сохранением дробной части.";
    private static final String NORMAL_INPUT = "Без аргументов и входных данных; 10 галлонов, коэффициент 3,7854.";
    private static final String NORMAL_EXPECTED = "Вторая величина 37.854 с абсолютным допуском 1e-8.";
    private static final String BOUNDARY_NAME = "Полный результат без ввода";
    private static final String BOUNDARY_WHAT = "Порядок двух результатов и самостоятельный запуск.";
    private static final String BOUNDARY_INPUT = "Пустой завершённый stdin, список аргументов пуст.";
    private static final String BOUNDARY_EXPECTED = "10 37.854; пробельное оформление свободно.";
    public static JavaProcess.Result run(String input,String...args) throws Exception {return JavaProcess.runCheckingUncaught(Main.class,input,args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run("");}
    public static void checkNormal(JavaProcess.Result r){double[] n=coursesupport.OutputNumbers.finiteNumbers(r.stdout());require(n.length==2&&Math.abs(n[1]-37.854)<=1e-8,"Проверьте второе число: 37.854 литра.");}
    public static void checkBoundary(JavaProcess.Result r){double[] n=coursesupport.OutputNumbers.finiteNumbers(r.stdout());require(n.length==2&&Math.abs(n[0]-10)<=1e-8&&Math.abs(n[1]-37.854)<=1e-8,"Нужны два числа в порядке галлоны, литры: 10 и 37.854.");}

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
