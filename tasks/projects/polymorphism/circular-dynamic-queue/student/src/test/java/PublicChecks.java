import java.util.*;
public class PublicChecks {
public record Observation(String actual,boolean passed){}
static final String NORMAL_NAME="Переход и рост: FIFO";
static final String NORMAL_WHAT="Фиксированная последовательность сохраняет оставшиеся C,D,E,F.";
static final String NORMAL_INPUT="DynQueue(3): put A,B,C; get A,B; put D,E,F; get4";
static final String NORMAL_EXPECTED="A,B затем C,D,E,F; операции молчат. Физический splitgrowth предъявляется отдельно.";
static final String BOUNDARY_NAME="Пустая очередь и заполнение";
static final String BOUNDARY_WHAT="Отказ get сохраняет возможность продолжить работу.";
static final String BOUNDARY_INPUT="DynQueue(1): get; put Q; get";
static final String BOUNDARY_EXPECTED="Очередь пуста и U+0000; затем Q без сообщения.";
static String lf(String s){return s.replace("\r\n","\n");}
public static Observation observeNormal(){Object q=TaskBinding.queue(3);char[] got=new char[6];String printed=PublicOutput.capture(()->{for(char c:new char[]{'A','B','C'})TaskBinding.put(q,c);got[0]=TaskBinding.get(q);got[1]=TaskBinding.get(q);for(char c:new char[]{'D','E','F'})TaskBinding.put(q,c);for(int i=2;i<6;i++)got[i]=TaskBinding.get(q);});return new Observation("Извлечено="+Arrays.toString(got)+"; вывод="+PublicOutput.escaped(printed)+"; физическое хранение предъявляется преподавателю отдельно.",Arrays.equals(got,new char[]{'A','B','C','D','E','F'})&&printed.isEmpty());}
public static Observation observeBoundary(){Object q=TaskBinding.queue(1);char[] got=new char[2];String empty=PublicOutput.capture(()->got[0]=TaskBinding.get(q));String success=PublicOutput.capture(()->{TaskBinding.put(q,'Q');got[1]=TaskBinding.get(q);});return new Observation("Первый код="+(int)got[0]+"; вывод="+PublicOutput.escaped(empty)+"; повторное извлечение="+got[1]+"; успешный вывод="+PublicOutput.escaped(success),got[0]==0&&got[1]=='Q'&&lf(empty).equals("Очередь пуста\n")&&success.isEmpty());}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычное наблюдение с условием.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте опубликованную границу с условием.");}
static int example(boolean normal){System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что наблюдаем: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());System.out.println(o.passed()?"Статус: наблюдаемое свойство соответствует; исходник и объяснение оценивает преподаватель.":"Статус: наблюдаемое свойство не соответствует.");return o.passed()?0:1;}catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}catch(RuntimeException e){System.out.println("Статус: операция или пример привязки не завершены корректно. Тип: "+e.getClass().getSimpleName());return 1;}catch(Error e){System.out.println("Статус: ошибка выполнения операции. Тип: "+e.getClass().getSimpleName());return 1;}}
public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
