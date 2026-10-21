import java.util.*;
/** Только два PUBLIC-сценария; TaskBinding — изменяемый пример клиента. */
public class PublicChecks {public record Observation(String actual,boolean passed){}public static void checkNormal(Observation value){if(!value.passed())throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием.");}public static void checkBoundary(Observation value){if(!value.passed())throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием.");}
public static final String NORMAL_NAME="Максимум внутри выбранного диапазона";
public static final String BOUNDARY_NAME="Диапазон из одной строки";
public static final String REQUIREMENT="Один обобщённый метод возвращает исходный максимальный элемент полуоткрытого диапазона и сохраняет массив.";
public static final String NORMAL_INPUT="Integer[99,2,7,4], [1,4)";
public static final String BOUNDARY_INPUT="String[я,б,в], [1,2)";
public static final String NORMAL_EXPECTED="Сам Integer из позиции2:7; массив сохранён.";
public static final String BOUNDARY_EXPECTED="Сама String из позиции1:б; массив сохранён.";
private static boolean unchanged(Object[]before,Object[]after){if(before.length!=after.length)return false;for(int i=0;i<before.length;i++)if(before[i]!=after[i])return false;return true;}
public static Observation observeNormal(){Integer[]a={99,2,7,4},before=a.clone();Integer result=TaskBinding.maximum(a,1,4);return new Observation("Integer результат="+result+", исходный элемент="+(result==before[2])+", массив="+Arrays.toString(a),result==before[2]&&unchanged(before,a));}
public static Observation observeBoundary(){String[]a={new String("я"),new String("б"),new String("в")},before=a.clone();String result=TaskBinding.maximum(a,1,2);return new Observation("String результат="+result+", исходный элемент="+(result==before[1])+", массив="+Arrays.toString(a),result==before[1]&&unchanged(before,a));}
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
