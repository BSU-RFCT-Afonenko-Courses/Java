import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Разные числовые значения истинности";
    private static final String NORMAL_WHAT = "Сочетание P=1,Q=0.";
    private static final String NORMAL_INPUT = "Демонстрация таблицы без новых входных параметров.";
    private static final String NORMAL_EXPECTED = "Строка 1 0 0 1 1 0; оформление колонок свободно.";
    private static final String BOUNDARY_NAME = "Оба значения ложны";
    private static final String BOUNDARY_WHAT = "P=0,Q=0 в полном конечном наборе.";
    private static final String BOUNDARY_INPUT = "Тот же запуск; найти пару 0,0 и все четыре сочетания.";
    private static final String BOUNDARY_EXPECTED = "Строка 0 0 0 0 0 1; пары встречаются по одному разу.";
    public static JavaProcess.Result run(String... args) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),"",args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run();}
    public static JavaProcess.Result observeBoundary() throws Exception{return run();}
    public static void checkNormal(JavaProcess.Result r){require(java.util.Arrays.equals(PublicBinding.table(r.stdout()).get("1/0"),new int[]{1,0,0,1,1,0}),"Проверьте сочетание 1,0 и отрицание P.");}
    public static void checkBoundary(JavaProcess.Result r){var rows=PublicBinding.table(r.stdout());require(rows.size()==4&&java.util.Arrays.equals(rows.get("0/0"),new int[]{0,0,0,0,0,1}),"Проверьте строку 0,0 и четыре различные пары.");}

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
