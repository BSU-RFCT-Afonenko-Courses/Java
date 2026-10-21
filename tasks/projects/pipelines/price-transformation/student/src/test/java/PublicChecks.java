import java.util.*;import java.math.BigDecimal;
public class PublicChecks {
static final String NORMAL_NAME="Ставка, нулевая цена и повтор";
static final String NORMAL_WHAT="Новый список, значения с налогом, порядок и сохранение входа.";
static final String NORMAL_INPUT="[100,0,100], ставка0.20; заранее выбранные тип и HALF_UP/HALF_EVEN";
static final String NORMAL_EXPECTED="[120.00,0.00,120.00] в новом списке, исходник прежний";
static final String BOUNDARY_NAME="Пустой список при верхней границе ставки";
static final String BOUNDARY_WHAT="Новый пустой список без изменения исходного.";
static final String BOUNDARY_INPUT="Пустой список, ставка1";
static final String BOUNDARY_EXPECTED="Другой пустой список";
public record Observation(String actual,boolean passed){}
public static Observation observeNormal()throws Exception{return prices(new ArrayList<>(List.of(d("100"),d("0"),d("100"))),d("0.20"),List.of(d("120.00"),d("0.00"),d("120.00")));}
public static Observation observeBoundary()throws Exception{return prices(java.util.stream.Stream.<BigDecimal>empty().toList(),d("1"),List.of());}
public static BigDecimal d(String s){return new BigDecimal(s);}public static void values(List<BigDecimal>a,String...expected){if(a==null||a.size()!=expected.length)throw new AssertionError("Размер списка цен не соответствует");for(int i=0;i<expected.length;i++)if(a.get(i)==null||a.get(i).compareTo(d(expected[i]))!=0)throw new AssertionError("Значение цены не соответствует");}static Observation prices(List<BigDecimal>input,BigDecimal rate,List<BigDecimal>expected){String policy=TaskBinding.policyDescription();var before=new ArrayList<>(input);var result=TaskBinding.run(input,rate);boolean ok=result!=null&&TaskBinding.statePreserved(input,result)&&input.equals(before)&&result.size()==expected.size();if(ok)for(int i=0;i<result.size();i++)ok &= result.get(i)!=null&&result.get(i).compareTo(expected.get(i))==0;return new Observation("Выбранный договор привязки="+policy+"; вход после="+input+"; результат="+result+"; фактическая самостоятельность/состояние выбранного представления="+TaskBinding.statePreserved(input,result),ok);}
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
