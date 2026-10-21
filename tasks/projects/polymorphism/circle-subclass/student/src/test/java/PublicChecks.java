import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
 public static final String NORMAL_NAME="Размеры и площадь через базовую ссылку";
 public static final String BOUNDARY_NAME="Максимальный допустимый радиус";
 public static final String REQUIREMENT="Диаметр и имя одного радиуса; конечная площадь с допуском max(1e-9,1e-12*|πr²|).";
 public static final String NORMAL_INPUT="Circle(2), ссылка TwoDShape; чтение размеров/имени/area";
 public static final String BOUNDARY_INPUT="Circle(1e150), вызов через TwoDShape";
 public static final String NORMAL_EXPECTED="4,4,circle; площадь12.566370614359172 в допуске.";
 public static final String BOUNDARY_EXPECTED="2e150,2e150,circle; конечная площадьπ*10^300 в допуске.";

 private static Observation circle(double radius) {
  Object value=TaskBinding.circle(radius);double width=TaskBinding.width(value),height=TaskBinding.height(value),area=TaskBinding.area(value);String name=TaskBinding.name(value);
  double expected=Math.PI*radius*radius,tolerance=Math.max(1e-9,1e-12*Math.abs(expected));
  return new Observation("width="+width+", height="+height+", name="+name+", area="+area+", допуск="+tolerance,width==2*radius && height==2*radius && "circle".equals(name) && Double.isFinite(area) && Math.abs(area-expected)<=tolerance);
 }
 public static Observation observeNormal(){return circle(2);}
 public static Observation observeBoundary(){return circle(1e150);}
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
