import java.util.*;
/** Только два PUBLIC-сценария; TaskBinding — изменяемый пример клиента. */
public class PublicChecks {public record Observation(String actual,boolean passed){}public static void checkNormal(Observation value){if(!value.passed())throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием.");}public static void checkBoundary(Observation value){if(!value.passed())throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием.");}
public static final String NORMAL_NAME="LIFO и освободившееся место";
public static final String BOUNDARY_NAME="Ёмкость один и оба отказа";
public static final String REQUIREMENT="IGenStack<T> использует переданный T[]; новый стек пуст, LIFO возвращает помещённые ссылки, отказы сохраняют состояние.";
public static final String NORMAL_INPUT="IGenStack<String>, String[2]:push Лист/Ветка,pop,push Плод,pop/pop";
public static final String BOUNDARY_INPUT="IGenStack<Integer>, Integer[1] содержит42 до передачи:pop,push7,push8,pop,pop,push9,pop";
public static final String NORMAL_EXPECTED="Ветка,Плод,Лист — прежние ссылки, все операции успешны.";
public static final String BOUNDARY_EXPECTED="StackEmptyException,StackFullException,7,StackEmptyException,9; обе возвращённые ссылки прежние.";
@SuppressWarnings("unchecked")private static <E extends Throwable,R>R forward(Throwable error)throws E{throw(E)error;}
 public static Observation observeNormal(){try{return normalChecked();}catch(Throwable error){return PublicChecks.<RuntimeException,Observation>forward(error);}}
 public static Observation observeBoundary(){try{return boundaryChecked();}catch(Throwable error){return PublicChecks.<RuntimeException,Observation>forward(error);}}
private static Observation normalChecked()throws Exception{Object stack=TaskBinding.create(0,2);String a=new String("Лист"),b=new String("Ветка"),c=new String("Плод");TaskBinding.push(stack,a);TaskBinding.push(stack,b);Object x=TaskBinding.pop(stack);TaskBinding.push(stack,c);Object y=TaskBinding.pop(stack),z=TaskBinding.pop(stack);return new Observation("Извлечено="+Arrays.asList(x,y,z)+", прежние ссылки="+(x==b)+"/"+(y==c)+"/"+(z==a),x==b&&y==c&&z==a);}
private static Observation boundaryChecked()throws Exception{Object stack=TaskBinding.create(1,1);boolean empty1=false,full=false,empty2=false;try{TaskBinding.pop(stack);}catch(Exception e){if(!TaskBinding.empty(e))throw e;empty1=true;}Integer a=7,b=8,c=9;TaskBinding.push(stack,a);try{TaskBinding.push(stack,b);}catch(Exception e){if(!TaskBinding.full(e))throw e;full=true;}Object x=TaskBinding.pop(stack);try{TaskBinding.pop(stack);}catch(Exception e){if(!TaskBinding.empty(e))throw e;empty2=true;}TaskBinding.push(stack,c);Object y=TaskBinding.pop(stack);return new Observation("Отказы empty/full/empty="+empty1+"/"+full+"/"+empty2+", извлечено="+x+"/"+y+", прежние ссылки="+(x==a)+"/"+(y==c)+"; переданное хранилище требует чтения исходника",empty1&&full&&empty2&&x==a&&y==c);}
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
