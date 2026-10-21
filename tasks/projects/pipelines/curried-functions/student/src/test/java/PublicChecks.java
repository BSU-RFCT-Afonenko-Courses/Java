import java.util.*;import java.math.BigDecimal;
public class PublicChecks {
static final String NORMAL_NAME="Повторное применение сохранённых функций";
static final String NORMAL_WHAT="Три операции, по одной сохранённой функции на два вторых аргумента.";
static final String NORMAL_INPUT="+7: -2,3; [A-Z]+: ABC,Ab; split comma: a,,b, и x,y";
static final String NORMAL_EXPECTED="5,10; true,false; [a,пустая,b,пустая] и[x,y]";
static final String BOUNDARY_NAME="Крайние целые и пустые части";
static final String BOUNDARY_WHAT="Границы суммы и различимые конечные пустые части.";
static final String BOUNDARY_INPUT="-1000000: -1000000,1000000; .*: пустая,A; split comma: пустая,comma";
static final String BOUNDARY_EXPECTED="-2000000,0; true,true; длины частей1 и2";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{return functions(false);}
public static Observation observeBoundary()throws Exception{return functions(true);}
static String parts(String[] values){if(values==null)return "null";return Arrays.stream(values).map(PublicOutput::escaped).toList()+"; длина="+values.length;}static Observation functions(boolean edge){var sum=TaskBinding.addition(edge?-1000000:7);var match=TaskBinding.matcher(edge?".*":"[A-Z]+");var split=TaskBinding.splitter(",");if(sum==null||match==null||split==null)return new Observation("После первого аргумента функция отсутствует",false);int a=sum.applyAsInt(edge?-1000000:-2),b=sum.applyAsInt(edge?1000000:3);boolean c=match.test(edge?"":"ABC"),d=match.test(edge?"A":"Ab");String[] e=split.apply(edge?"":"a,,b,"),f=split.apply(edge?",":"x,y");boolean ok=a==(edge?-2000000:5)&&b==(edge?0:10)&&c&&d==edge&&Arrays.equals(e,edge?new String[]{""}:new String[]{"a","","b",""})&&Arrays.equals(f,edge?new String[]{"",""}:new String[]{"x","y"});return new Observation("Суммы="+a+","+b+"; совпадения="+c+","+d+"; части1="+parts(e)+"; части2="+parts(f),ok);}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычное наблюдение с опубликованным требованием.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдение с опубликованной границей.");}
static int example(boolean normal){System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(!o.passed()){System.out.println("Статус: наблюдаемый результат не соответствует опубликованному случаю.");return 1;}System.out.println("Статус: наблюдаемое свойство соответствует; исходник, обязательные средства, выбранные правила и объяснение оценивает преподаватель.");return 0;}
catch(coursesupport.JavaProcess.UncaughtStudentException e){System.out.println("Статус: необработанная ошибка выполнения программы.");return 1;}
catch(coursesupport.JavaProcess.StudentTimeout e){System.out.println("Статус: время выполнения превышено.");return 1;}
catch(coursesupport.JavaProcess.StudentOutputLimit e){System.out.println("Статус: превышен объём вывода.");return 1;}
catch(UnsupportedOperationException e){System.out.println("Статус: заготовка или выбранная привязка ещё не подготовлена; предъявите реализацию преподавателю.");return 1;}
catch(AssertionError e){System.out.println("Статус: операция завершилась ошибкой выполнения AssertionError; проверьте выбранный вызов.");return 1;}
catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
catch(Error e){System.out.println("Статус: операция завершилась ошибкой выполнения. Тип: "+e.getClass().getSimpleName());return 1;}
catch(Exception e){System.out.println("Статус: операция завершилась ошибкой; проверьте реализацию и выбранную привязку. Тип: "+e.getClass().getSimpleName());return 1;}}
public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
