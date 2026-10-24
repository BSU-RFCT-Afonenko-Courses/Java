import java.util.*;
/** Только два PUBLIC-сценария; TaskBinding — изменяемый пример клиента. */
public class PublicChecks {public record Observation(String actual,boolean passed){}public static void checkNormal(Observation value){if(!value.passed())throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием.");}public static void checkBoundary(Observation value){if(!value.passed())throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием.");}
public static final String NORMAL_NAME="FIFO и освободившееся место";
public static final String BOUNDARY_NAME="Ёмкость один и оба отказа";
public static final String REQUIREMENT="Переданный T[] задаёт вместимость; новая очередь пуста, FIFO возвращает помещённые ссылки, отказы сохраняют состояние.";
public static final String NORMAL_INPUT="GenericQueue<String>, String[2]:put Лист/Ветка,get,put Плод,get/get";
public static final String BOUNDARY_INPUT="GenericQueue<Integer>, Integer[1] содержит42 до передачи: get,put7,put8,get,get,put9,get";
public static final String NORMAL_EXPECTED="Лист,Ветка,Плод — прежние ссылки, все операции успешны.";
public static final String BOUNDARY_EXPECTED="QueueEmptyException,QueueFullException,7,QueueEmptyException,9; обе возвращённые ссылки прежние.";
@SuppressWarnings("unchecked")private static <E extends Throwable,R>R forward(Throwable error)throws E{throw(E)error;}
 public static Observation observeNormal(){try{return normalChecked();}catch(Throwable error){return PublicChecks.<RuntimeException,Observation>forward(error);}}
 public static Observation observeBoundary(){try{return boundaryChecked();}catch(Throwable error){return PublicChecks.<RuntimeException,Observation>forward(error);}}
private static Observation normalChecked()throws Throwable{var access=TaskBinding.access();Object queue=access.create(new String[2]);String a=new String("Лист"),b=new String("Ветка"),c=new String("Плод");access.put(queue,a);access.put(queue,b);Object x=access.get(queue);access.put(queue,c);Object y=access.get(queue),z=access.get(queue);return new Observation("Извлечено="+Arrays.asList(x,y,z)+", прежние ссылки="+(x==a)+"/"+(y==b)+"/"+(z==c),x==a&&y==b&&z==c);}
private static Observation boundaryChecked()throws Throwable{var access=TaskBinding.access();Object queue=access.create(new Integer[]{42});boolean empty1=false,full=false,empty2=false;try{access.get(queue);}catch(Throwable e){if(!access.empty.isInstance(e))throw e;empty1=true;}Integer a=7,b=8,c=9;access.put(queue,a);try{access.put(queue,b);}catch(Throwable e){if(!access.full.isInstance(e))throw e;full=true;}Object x=access.get(queue);try{access.get(queue);}catch(Throwable e){if(!access.empty.isInstance(e))throw e;empty2=true;}access.put(queue,c);Object y=access.get(queue);return new Observation("Отказы empty/full/empty="+empty1+"/"+full+"/"+empty2+", извлечено="+x+"/"+y+", прежние ссылки="+(x==a)+"/"+(y==c)+"; переданное хранилище требует чтения исходника",empty1&&full&&empty2&&x==a&&y==c);}
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
