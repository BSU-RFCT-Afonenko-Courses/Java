import java.util.Arrays;
/** Только опубликованные данные. TaskBinding — редактируемый клиент, не обязательный API студента. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Все позиции общего максимума";
    private static final String NORMAL_WHAT = "Совместное исключение строк и столбцов по исходным позициям.";
    private static final String NORMAL_INPUT = "Фиксированная матрица с максимумом3 в (0,2) и (2,1).";
    private static final String NORMAL_EXPECTED = "Размер1×1, единственное значение2.";
    private static final String BOUNDARY_NAME = "Пустые результаты с разными размерностями";
    private static final String BOUNDARY_WHAT = "Отсутствие строк и отсутствие столбцов.";
    private static final String BOUNDARY_INPUT = "Отдельно {{9,1},{9,2}} и {{9,9},{1,2}}.";
    private static final String BOUNDARY_EXPECTED = "Первая даёт0×1, вторая1×0; элементов нет, размеры самостоятельны.";
    public static Observation observeNormal(){int[] r=TaskBinding.run(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}});return new Observation(show(r),Arrays.equals(new int[]{1,1,2},r));}
    public static Observation observeBoundary(){int[] a=TaskBinding.run(new int[][]{{9,1},{9,2}}),b=TaskBinding.run(new int[][]{{9,9},{1,2}});return new Observation("первая: "+show(a)+"; вторая: "+show(b),Arrays.equals(new int[]{0,1},a)&&Arrays.equals(new int[]{1,0},b));}
    static String show(int[] r){if(r==null||r.length<2)return "Не наблюдены обе размерности: "+Arrays.toString(r);return "размер "+r[0]+"×"+r[1]+", значения "+Arrays.toString(Arrays.copyOfRange(r,2,r.length));}
    public record Observation(String actual,boolean passed) {}
    public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте наблюдаемый результат с опубликованным обычным случаем.");}
    public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте состояние с опубликованной границей.");}
    static int example(boolean normal){
      System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));
      System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));
      System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));
      System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
      try {var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует. Исходник, показ и объяснение оценивает преподаватель.");return 0;}
      catch(UnsupportedOperationException e){System.out.println("Статус: реализация примера ещё не завершена; проверьте исходник и привязку.");return 1;}
      catch(AssertionError e){System.out.println("Статус: неверный наблюдаемый результат; сопоставьте с опубликованным случаем.");return 1;}
      catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
      catch(RuntimeException e){System.out.println("Статус: ошибка выполнения выбранного примера; проверьте реализацию и привязку, требуется разбор преподавателя.");return 1;}
    }
    public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
