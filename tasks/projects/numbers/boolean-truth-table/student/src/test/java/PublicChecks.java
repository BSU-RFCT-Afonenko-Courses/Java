import coursesupport.JavaProcess;

/** Только два опубликованных PUBLIC наблюдения; JDK 25 без JUnit. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Разные значения P и Q";
    private static final String NORMAL_WHAT = "Сочетание true,false и NOT(P).";
    private static final String NORMAL_INPUT = "Запуск без данных; найти пару true,false.";
    private static final String NORMAL_EXPECTED = "Заголовок с табуляциями; true\\tfalse\\tfalse\\ttrue\\ttrue\\tfalse.";
    private static final String BOUNDARY_NAME = "Оба операнда ложны";
    private static final String BOUNDARY_WHAT = "False,false в полном наборе четырёх пар.";
    private static final String BOUNDARY_INPUT = "Тот же запуск; сопоставить пары независимо от порядка.";
    private static final String BOUNDARY_EXPECTED = "false\\tfalse\\tfalse\\tfalse\\tfalse\\ttrue; четыре разные пары.";
    public static JavaProcess.Result run(String input,String...args) throws Exception {return JavaProcess.runCheckingUncaught(Main.class,input,args);}

    public static JavaProcess.Result observeNormal() throws Exception{return run("");}
    public static JavaProcess.Result observeBoundary() throws Exception{return run("");}
    public static java.util.Map<String,String[]> table(String out){
        String text=out.replace("\r\n","\n");if(text.endsWith("\n"))text=text.substring(0,text.length()-1);
        String[] lines=text.split("\n",-1);require(lines.length==5,"Нужны заголовок и четыре строки данных.");
        require(lines[0].equals("P\tQ\tAND\tOR\tXOR\tNOT"),"Проверьте точный заголовок и табуляции.");
        var rows=new java.util.HashMap<String,String[]>();
        for(int i=1;i<5;i++){String[] r=lines[i].split("\t",-1);require(r.length==6,"Между шестью колонками нужны табуляции.");for(String v:r)require(v.equals("true")||v.equals("false"),"Значения должны быть true или false.");require(rows.put(r[0]+"/"+r[1],r)==null,"Каждая пара P,Q встречается один раз.");}
        return rows;
    }
    public static void checkNormal(JavaProcess.Result r){require(java.util.Arrays.equals(table(r.stdout()).get("true/false"),new String[]{"true","false","false","true","true","false"}),"Проверьте строку true,false, включая NOT(P).");}
    public static void checkBoundary(JavaProcess.Result r){var rows=table(r.stdout());require(rows.size()==4&&java.util.Arrays.equals(rows.get("false/false"),new String[]{"false","false","false","false","false","true"}),"Проверьте строку false,false и полный набор четырёх пар.");}

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
