import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Простое и составное внутри диапазона";
    private static final String NORMAL_WHAT = "7 один раз, 9 отсутствует.";
    private static final String NORMAL_INPUT = "Запуск без входных данных для 2..100.";
    private static final String NORMAL_EXPECTED = "Возрастающая последовательность с 7 без 9.";
    private static final String BOUNDARY_NAME = "Границы фиксированного диапазона";
    private static final String BOUNDARY_WHAT = "Нижняя граница 2 и составное 100.";
    private static final String BOUNDARY_INPUT = "Тот же запуск без данных.";
    private static final String BOUNDARY_EXPECTED = "Первое 2, последнее 97; 1 и 100 отсутствуют; повторов нет.";
    public static JavaProcess.Result run(String input) throws Exception {return JavaProcess.runCheckingUncaught(PublicBinding.entry(),input);}
    static void exact(String expected,String actual){require(actual.equals(expected)||actual.equals(expected+"\n")||actual.equals(expected+"\r\n"),"Сопоставьте точный текст результата и опубликованный перевод строки.");}
    static String[] tokens(String text){return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");}
    static void numbers(String expected,String actual){String[] a=tokens(expected),b=tokens(actual);require(a.length==b.length,"Сохраните количество выбранных значений.");try{for(int i=0;i<a.length;i++)require(new java.math.BigInteger(a[i]).equals(new java.math.BigInteger(b[i])),"Сохраните значения, знак, порядок и повторные вхождения чисел.");}catch(NumberFormatException bad){throw new AssertionError("Выведите целые числа с пробельными разделителями.");}}

    public static int[] values(String out){try{return java.util.Arrays.stream(tokens(out)).mapToInt(Integer::parseInt).toArray();}catch(NumberFormatException bad){throw new AssertionError("Нужны целые числа с пробельными разделителями.");}}
    public static void ordered(int[] a){for(int i=1;i<a.length;i++)require(a[i-1]<a[i],"Числа идут строго по возрастанию, без повторов.");}
    public static JavaProcess.Result observeNormal()throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run("");}
    public static void checkNormal(JavaProcess.Result r){int[] a=values(r.stdout());ordered(a);require(java.util.Arrays.stream(a).filter(x->x==7).count()==1&&!java.util.Arrays.stream(a).anyMatch(x->x==9),"Нужно простое 7 без составного 9.");}
    public static void checkBoundary(JavaProcess.Result r){int[] a=values(r.stdout());ordered(a);require(a.length>0&&a[0]==2&&a[a.length-1]==97&&!java.util.Arrays.stream(a).anyMatch(x->x==1||x==100),"Проверьте границы 2 и 100.");}

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
