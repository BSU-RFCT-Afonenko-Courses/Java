import java.util.*;
public class PublicChecks {
public record Observation(String actual,boolean passed){}
static final String NORMAL_NAME="Поддерживаемый сброс";
static final String NORMAL_WHAT="B исчезает, новая очередь принимает C после сброса.";
static final String NORMAL_INPUT="Новая: put A,B; get A; reset; get; put C; get";
static final String NORMAL_EXPECTED="Пустой get: Очередь пуста/U+0000; затем C, успешные put/get молчат; вывод/reset-return свободен.";
static final String BOUNDARY_NAME="Старая реализация без сброса";
static final String BOUNDARY_WHAT="Унаследованный default отказывает без удаления A.";
static final String BOUNDARY_INPUT="Старая только put/get: put A; reset; get";
static final String BOUNDARY_EXPECTED="UnsupportedOperationException; затем A без сообщения. Совместимость исходника отдельно по реальной компиляции.";
static String lf(String s){return s.replace("\r\n","\n");}
public static Observation observeNormal(){Object q=TaskBinding.newQueue();char[] got=new char[3];String begin=PublicOutput.capture(()->{TaskBinding.put(q,'A');TaskBinding.put(q,'B');got[0]=TaskBinding.get(q);});String resetOutput=PublicOutput.capture(()->TaskBinding.reset(q));String empty=PublicOutput.capture(()->got[1]=TaskBinding.get(q));String end=PublicOutput.capture(()->{TaskBinding.put(q,'C');got[2]=TaskBinding.get(q);});return new Observation("Извлечено="+got[0]+",U+"+String.format("%04X",(int)got[1])+","+got[2]+"; пустой get="+PublicOutput.escaped(empty)+"; вывод reset (свободен)="+PublicOutput.escaped(resetOutput),got[0]=='A'&&got[1]==0&&got[2]=='C'&&begin.isEmpty()&&end.isEmpty()&&lf(empty).equals("Очередь пуста\n"));}
public static Observation observeBoundary(){Object q=TaskBinding.oldQueue();char[] got=new char[1];String put=PublicOutput.capture(()->TaskBinding.put(q,'A'));boolean unsupported=false;try{TaskBinding.reset(q);}catch(UnsupportedOperationException e){unsupported=true;}String get=PublicOutput.capture(()->got[0]=TaskBinding.get(q));return new Observation("UnsupportedOperationException="+unsupported+"; извлечено="+got[0]+"; put/get="+PublicOutput.escaped(put+get),unsupported&&got[0]=='A'&&put.isEmpty()&&get.isEmpty());}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычное наблюдение с условием.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте опубликованную границу с условием.");}
static int example(boolean normal){System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что наблюдаем: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());System.out.println(o.passed()?"Статус: наблюдаемое свойство соответствует; исходник и объяснение оценивает преподаватель.":"Статус: наблюдаемое свойство не соответствует.");return o.passed()?0:1;}catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}catch(RuntimeException e){System.out.println("Статус: операция или пример привязки не завершены корректно. Тип: "+e.getClass().getSimpleName());return 1;}catch(Error e){System.out.println("Статус: ошибка выполнения операции. Тип: "+e.getClass().getSimpleName());return 1;}}
public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
