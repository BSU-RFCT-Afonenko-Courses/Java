import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Шестнадцатеричная запись";
    private static final String NORMAL_WHAT = "Обычное преобразование с буквенной цифрой.";
    private static final String NORMAL_INPUT = "Аргументы 31 16.";
    private static final String NORMAL_EXPECTED = "1F либо 1f, без ведущего нуля.";
    private static final String BOUNDARY_NAME = "Нуль при наибольшем основании";
    private static final String BOUNDARY_WHAT = "Границы числа 0 и основания 36.";
    private static final String BOUNDARY_INPUT = "Аргументы 0 36.";
    private static final String BOUNDARY_EXPECTED = "0; не пустая строка и не несколько нулей.";
    public static JavaProcess.Result run(String... args) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),"",args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("31","16");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run("0","36");}
    public static void representation(JavaProcess.Result r,String decimal,int radix){String value=r.stdout().strip();require(value.matches("[0-9a-z]+")||value.matches("[0-9A-Z]+"),"Нужна позиционная запись с буквами одного регистра.");require(value.length()==1||!value.startsWith("0"),"Ведущие нули отсутствуют, кроме самого нуля.");try{require(new java.math.BigInteger(value,radix).equals(new java.math.BigInteger(decimal)),"Запись должна сохранять исходное целое.");}catch(NumberFormatException e){throw new AssertionError("Цифры должны соответствовать основанию.");}}
    public static void checkNormal(JavaProcess.Result r){representation(r,"31",16);}
    public static void checkBoundary(JavaProcess.Result r){representation(r,"0",36);}

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
