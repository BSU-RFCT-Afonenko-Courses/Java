import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Произведения внутри таблицы";
    private static final String NORMAL_WHAT = "Третья строка, четвёртый столбец.";
    private static final String NORMAL_INPUT = "Запуск без входных данных.";
    private static final String NORMAL_EXPECTED = "3 6 9 12 15 18 21 24 27 30; четвёртое число 12.";
    private static final String BOUNDARY_NAME = "Последняя строка и столбец";
    private static final String BOUNDARY_WHAT = "Десять строк по десять чисел, верхняя граница 10.";
    private static final String BOUNDARY_INPUT = "Тот же запуск без данных.";
    private static final String BOUNDARY_EXPECTED = "10 20 30 40 50 60 70 80 90 100.";
    public static JavaProcess.Result run(String input) throws Exception {return JavaProcess.runCheckingUncaught(Main.class,input);}
    static void exact(String expected,String actual){require(actual.equals(expected)||actual.equals(expected+"\n")||actual.equals(expected+"\r\n"),"Сопоставьте точный текст результата и опубликованный перевод строки.");}
    static String[] tokens(String text){return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");}
    static void numbers(String expected,String actual){String[] a=tokens(expected),b=tokens(actual);require(a.length==b.length,"Сохраните количество выбранных значений.");try{for(int i=0;i<a.length;i++)require(new java.math.BigInteger(a[i]).equals(new java.math.BigInteger(b[i])),"Сохраните значения, знак, порядок и повторные вхождения чисел.");}catch(NumberFormatException bad){throw new AssertionError("Выведите целые числа с пробельными разделителями.");}}

    public static String[] rows(String out){var r=new java.util.ArrayList<>(java.util.Arrays.asList(out.split("\r\n|\n|\r",-1)));if(!r.isEmpty()&&r.getLast().isEmpty())r.removeLast();require(r.size()==10,"Нужны десять строк произведений.");for(String row:r)require(tokens(row).length==10,"В каждой строке нужны десять чисел.");return r.toArray(String[]::new);}
    public static JavaProcess.Result observeNormal()throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run("");}
    public static void checkNormal(JavaProcess.Result r){numbers("3 6 9 12 15 18 21 24 27 30",rows(r.stdout())[2]);}
    public static void checkBoundary(JavaProcess.Result r){numbers("10 20 30 40 50 60 70 80 90 100",rows(r.stdout())[9]);}

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
