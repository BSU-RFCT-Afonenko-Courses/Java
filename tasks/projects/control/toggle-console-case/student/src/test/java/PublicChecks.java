import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "ASCII рядом с Unicode";
    private static final String NORMAL_WHAT = "Преобразование только ASCII с сохранением других символов.";
    private static final String NORMAL_INPUT = "UTF-8 stdin AБяz 👋🙂., затем EOF.";
    private static final String NORMAL_EXPECTED = "aБяZ 👋🙂, дополнительный перевод строки, 2 и перевод строки.";
    private static final String BOUNDARY_NAME = "Точка в начале";
    private static final String BOUNDARY_WHAT = "Пустая часть и игнорирование данных после точки.";
    private static final String BOUNDARY_INPUT = "UTF-8 stdin .Az, затем EOF.";
    private static final String BOUNDARY_EXPECTED = "Пустая строка, затем 0 с переводом строки.";
    public static JavaProcess.Result run(String input) throws Exception {return JavaProcess.runCheckingUncaught(Main.class,input);}
    static void exact(String expected,String actual){require(actual.equals(expected)||actual.equals(expected+"\n")||actual.equals(expected+"\r\n"),"Сопоставьте точный текст результата и опубликованный перевод строки.");}
    static String[] tokens(String text){return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");}
    static void numbers(String expected,String actual){String[] a=tokens(expected),b=tokens(actual);require(a.length==b.length,"Сохраните количество выбранных значений.");try{for(int i=0;i<a.length;i++)require(new java.math.BigInteger(a[i]).equals(new java.math.BigInteger(b[i])),"Сохраните значения, знак, порядок и повторные вхождения чисел.");}catch(NumberFormatException bad){throw new AssertionError("Выведите целые числа с пробельными разделителями.");}}

    public static void transformed(String text,int count,String actual){boolean ok=false;for(String first:new String[]{"\n","\r\n"})for(String last:new String[]{"\n","\r\n"})ok|=actual.equals(text+first+count+last);require(ok,"Сохраните весь текст, счётчик и оба дополнительных перевода строки.");}
    public static JavaProcess.Result observeNormal()throws Exception{return run("AБяz 👋🙂.");}
    public static JavaProcess.Result observeBoundary()throws Exception{return run(".Az");}
    public static void checkNormal(JavaProcess.Result r){transformed("aБяZ 👋🙂",2,r.stdout());}
    public static void checkBoundary(JavaProcess.Result r){transformed("",0,r.stdout());}

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
