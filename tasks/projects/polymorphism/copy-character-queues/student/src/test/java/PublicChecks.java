import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
 public static final String NORMAL_NAME="Выросший источник и кольцевой приёмник";
 public static final String BOUNDARY_NAME="Нулевой перенос в исчерпанный линейный приёмник";
 public static final String REQUIREMENT="Перенос только put/get сохраняет FIFO и объекты, опустошает источник; count0 не требует места.";
 public static final String NORMAL_INPUT="Dyn(2) содержит A/B/C; Circular(3) пуст; transfer3, чтение приёмника и пустого источника";
 public static final String BOUNDARY_INPUT="Circular(1) пуст; Fixed(1) послеput/get Q пуст, линейное место исчерпано; transfer0";
 public static final String NORMAL_EXPECTED="Transfer молчит; приёмник A/B/C; get источника после transfer даёт0 и строку Очередь пуста.";
 public static final String BOUNDARY_EXPECTED="Сам transfer молчит; обе прежние очереди пусты, без reset или новых помещений.";

 private static String capture(Runnable action){PrintStream old=System.out;ByteArrayOutputStream bytes=new ByteArrayOutputStream();try(PrintStream out=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(out);action.run();}finally{System.setOut(old);}return bytes.toString(StandardCharsets.UTF_8);}

 private static String normalized(String text){return text.replace("\r\n","\n");}
 private static String shown(String text){return text.isEmpty()?"Пустой стандартный вывод.":"\""+text.replace("\\","\\\\").replace("\"","\\\"").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t")+"\"";}
 public static Observation observeNormal(){
  Object source=TaskBinding.queue(2,2),destination=TaskBinding.queue(1,3),oldSource=source,oldDestination=destination;
  String fill=capture(()->{TaskBinding.put(source,'A');TaskBinding.put(source,'B');TaskBinding.put(source,'C');});String transfer=capture(()->TaskBinding.transfer(source,destination,3));char[] result=new char[3];String read=capture(()->{for(int i=0;i<3;i++)result[i]=TaskBinding.get(destination);});char[] empty=new char[1];String sourceMessage=capture(()->empty[0]=TaskBinding.get(source));
  return new Observation("Приёмник="+Arrays.toString(result)+",transfer сообщения="+shown(transfer)+",пустой source код="+(int)empty[0]+",сообщение="+shown(sourceMessage),source==oldSource&&destination==oldDestination&&fill.isEmpty()&&transfer.isEmpty()&&read.isEmpty()&&Arrays.equals(result,new char[]{'A','B','C'})&&empty[0]=='\0'&&normalized(sourceMessage).equals("Очередь пуста\n"));
 }
 public static Observation observeBoundary(){
  Object source=TaskBinding.queue(1,1),destination=TaskBinding.queue(0,1),oldSource=source,oldDestination=destination;char[] before=new char[1];String setup=capture(()->{TaskBinding.put(destination,'Q');before[0]=TaskBinding.get(destination);});String transfer=capture(()->TaskBinding.transfer(source,destination,0));char[] after=new char[2];String messages=capture(()->{after[0]=TaskBinding.get(source);after[1]=TaskBinding.get(destination);});
  return new Observation("Исчерпано помещение Q,извлечено="+before[0]+",transfer сообщения="+shown(transfer)+",пустые возвраты="+Arrays.toString(new int[]{after[0],after[1]})+",get после переноса="+shown(messages),source==oldSource&&destination==oldDestination&&setup.isEmpty()&&before[0]=='Q'&&transfer.isEmpty()&&Arrays.equals(after,new char[]{'\0','\0'})&&normalized(messages).equals("Очередь пуста\nОчередь пуста\n"));
 }
    public static void main(String[] args) {
        boolean success=true;
        for (String id: new String[]{"normal","boundary"}) {
            System.out.println("Проверка: " + (id.equals("normal") ? NORMAL_NAME : BOUNDARY_NAME));
            System.out.println("Требование: " + REQUIREMENT);
            System.out.println("Вход/действие: " + (id.equals("normal") ? NORMAL_INPUT : BOUNDARY_INPUT));
            System.out.println("Ожидается: " + (id.equals("normal") ? NORMAL_EXPECTED : BOUNDARY_EXPECTED));
            Observation result;
            try { result=id.equals("normal")?observeNormal():observeBoundary(); }
            catch (Throwable error) {
                System.out.println("Получено: выполнение операции завершилось исключением " + error.getClass().getSimpleName());
                System.out.println("Статус: ошибка выполнения. Проверьте действие и согласование клиента с API решения.");success=false;continue;
            }
            System.out.println("Получено: " + result.actual());
            System.out.println("Статус: " + (result.passed()?"наблюдаемое поведение совпало; обязательная ручная часть остаётся":"требует исправления"));
            success &= result.passed();
        }
        if (!success) System.exit(1);
    }
}
