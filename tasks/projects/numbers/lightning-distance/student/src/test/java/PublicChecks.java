import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Расстояние по заданной задержке";
    private static final String NORMAL_WHAT = "335 м/с и 7,2 с.";
    private static final String NORMAL_INPUT = "Запуск без аргументов и входных данных.";
    private static final String NORMAL_EXPECTED = "Первая непустая строка: только 2412 с абсолютным допуском 1e-8.";
    private static final String BOUNDARY_NAME = "Граница обязательного результата";
    private static final String BOUNDARY_WHAT = "Дополнительное эхо не является автоматическим критерием.";
    private static final String BOUNDARY_INPUT = "Тот же запуск без данных; дополнительная часть может отсутствовать.";
    private static final String BOUNDARY_EXPECTED = "Первая непустая строка 2412; допустимый последующий вывод не отвергается.";
    public static JavaProcess.Result run(String input,String...args) throws Exception {return JavaProcess.runCheckingUncaught(Main.class,input,args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run("");}
    public static void lightning(String out){
        String first=out.lines().filter(line->!line.isBlank()).findFirst().orElse("");
        double[] n=coursesupport.OutputNumbers.finiteNumbers(first);require(n.length==1&&Math.abs(n[0]-2412)<=1e-8,"Первая непустая строка должна содержать только расстояние до молнии 2412 м.");
    }
    public static void checkNormal(JavaProcess.Result r){lightning(r.stdout());}
    public static void checkBoundary(JavaProcess.Result r){lightning(r.stdout());}

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
