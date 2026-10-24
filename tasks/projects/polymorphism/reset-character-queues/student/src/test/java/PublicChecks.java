import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
 public static final String NORMAL_NAME="Сброс после частичного извлечения";
 public static final String BOUNDARY_NAME="Пустой сброс и уже выросшая очередь";
 public static final String REQUIREMENT="FIFO после reset всех трёх; текущая ёмкость сохраняется и проверяется по исходнику отдельно.";
 public static final String NORMAL_INPUT="Fixed/Circular/Dyn(2):put A/B,get A,reset,put X/Y,get/get";
 public static final String BOUNDARY_INPUT="Fixed/Circular(1) пусты; Dyn(1) содержит A/B/C; reset, повторный пустой reset Dyn,put X,get,get пустой";
 public static final String NORMAL_EXPECTED="Все три дают X/Y, старый B удалён; успешные операции молчат. Ёмкость: отдельное чтение.";
 public static final String BOUNDARY_EXPECTED="Все дают X, затем0 и строку Очередь пуста. Пустые reset молчат. Dyn сохраняет фактическое k>=3: отдельное чтение.";

 private static String capture(Runnable action){PrintStream old=System.out;ByteArrayOutputStream bytes=new ByteArrayOutputStream();try(PrintStream out=new PrintStream(bytes,true,StandardCharsets.UTF_8)){System.setOut(out);action.run();}finally{System.setOut(old);}return bytes.toString(StandardCharsets.UTF_8);}

 private static String normalized(String text){return text.replace("\r\n","\n");}
 private static String shown(String text){return text.isEmpty()?"Пустой стандартный вывод.":"\""+text.replace("\\","\\\\").replace("\"","\\\"").replace("\r","\\r").replace("\n","\\n").replace("\t","\\t")+"\"";}
 public static Observation observeNormal(){
  boolean okay=true;StringBuilder actual=new StringBuilder();
  for(int kind=0;kind<3;kind++){Object queue=TaskBinding.queue(kind,2);char[] result=new char[3];String text=capture(()->{TaskBinding.put(queue,'A');TaskBinding.put(queue,'B');result[0]=TaskBinding.get(queue);TaskBinding.reset(queue);TaskBinding.put(queue,'X');TaskBinding.put(queue,'Y');result[1]=TaskBinding.get(queue);result[2]=TaskBinding.get(queue);});
   okay &= Arrays.equals(result,new char[]{'A','X','Y'})&&text.isEmpty();actual.append("Очередь").append(new String[]{"FixedQueue","CircularQueue","DynQueue"}[kind]).append(" возвраты=").append(Arrays.toString(result)).append(",сообщения=").append(shown(text)).append("; ");}
  return new Observation(actual+"Текущая ёмкость автоматически не наблюдалась: требуется чтение исходника и объяснение.",okay);
 }
 public static Observation observeBoundary(){
  boolean okay=true;StringBuilder actual=new StringBuilder();
  for(int kind=0;kind<3;kind++){final int selected=kind;Object queue=TaskBinding.queue(kind,1);char[] result=new char[2];String text=capture(()->{if(selected==2){TaskBinding.put(queue,'A');TaskBinding.put(queue,'B');TaskBinding.put(queue,'C');}TaskBinding.reset(queue);if(selected==2)TaskBinding.reset(queue);TaskBinding.put(queue,'X');result[0]=TaskBinding.get(queue);result[1]=TaskBinding.get(queue);});okay &= Arrays.equals(result,new char[]{'X','\0'})&&normalized(text).equals("Очередь пуста\n");actual.append("Очередь").append(new String[]{"FixedQueue","CircularQueue","DynQueue"}[kind]).append(" коды=").append(Arrays.toString(new int[]{result[0],result[1]})).append(",сообщения=").append(shown(text)).append("; ");}
  return new Observation(actual+"Сохранение выросшей ёмкости k не доказано FIFO-наблюдением; проверьте источник (k>=3, без фиксированного коэффициента).",okay);
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
