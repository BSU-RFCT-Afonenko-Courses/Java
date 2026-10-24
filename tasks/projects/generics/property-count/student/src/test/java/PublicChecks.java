import java.util.*;
/** Только два PUBLIC-сценария; TaskBinding — изменяемый пример клиента. */
public class PublicChecks {public record Observation(String actual,boolean passed){}public static void checkNormal(Observation value){if(!value.passed())throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием.");}public static void checkBoundary(Observation value){if(!value.passed())throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием.");}
public static final String NORMAL_NAME="Два переданных свойства";
public static final String BOUNDARY_NAME="Пустые массивы двух типов";
public static final String REQUIREMENT="Количество определяется переданным объектом свойства; массив сохранён, пустой даёт0.";
public static final String NORMAL_INPUT="Integer[1,2,3], нечётность; String[one,four,пустая строка], длина>3";
public static final String BOUNDARY_INPUT="Пустые Integer[] и String[], те же свойства";
public static final String NORMAL_EXPECTED="2 и1, массивы сохранены.";
public static final String BOUNDARY_EXPECTED="0 и0; массивы сохранены.";
private static boolean unchanged(Object[]before,Object[]after){if(before.length!=after.length)return false;for(int i=0;i<before.length;i++)if(before[i]!=after[i])return false;return true;}
public static Observation observeNormal(){Integer[]a={1,2,3},before=a.clone();String[]s={new String("one"),new String("four"),new String("")},old=s.clone();int x=TaskBinding.integers(a,0),y=TaskBinding.strings(s,0);return new Observation("Нечётность="+x+", длина>3="+y+"; массивы="+Arrays.toString(a)+";"+Arrays.toString(s),x==2&&y==1&&unchanged(before,a)&&unchanged(old,s));}
public static Observation observeBoundary(){Integer[]a={};String[]s={};int x=TaskBinding.integers(a,0),y=TaskBinding.strings(s,0);return new Observation("Пустые массивы: Integer="+x+",String="+y+"; длины="+a.length+"/"+s.length,x==0&&y==0&&a.length==0&&s.length==0);}
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
