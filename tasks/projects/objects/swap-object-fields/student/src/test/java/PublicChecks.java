import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
    public static final String NORMAL_NAME="Два объекта сохраняют свою идентичность";
    public static final String BOUNDARY_NAME="Две ссылки на один объект";
    public static final String REQUIREMENT="Обмен значений a существующих Test; самопередача сохраняет состояние.";
    public static final String NORMAL_INPUT="Test(3), Test(8); swap(x,y)";
    public static final String BOUNDARY_INPUT="Test(-2); swap(x,x)";
    public static final String NORMAL_EXPECTED="Прежние x/y содержат8/3.";
    public static final String BOUNDARY_EXPECTED="Прежний x содержит-2.";

    public static Observation observeNormal() {
        Object x=TaskBinding.create(3),y=TaskBinding.create(8);Object oldX=x,oldY=y;
        TaskBinding.swap(x,y);int a=TaskBinding.value(oldX),b=TaskBinding.value(oldY);
        return new Observation("Прежние x/y: "+a+", "+b,a==8 && b==3 && x==oldX && y==oldY);
    }
    public static Observation observeBoundary() {
        Object x=TaskBinding.create(-2),old=x;TaskBinding.swap(x,x);int value=TaskBinding.value(old);
        return new Observation("Прежний объект: a="+value,value==-2 && x==old);
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
            System.out.println("Статус: " + (result.passed()?"совпадает с открытым ожиданием":"требует исправления"));
            success &= result.passed();
        }
        if (!success) System.exit(1);
    }
}
