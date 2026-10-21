import java.util.Arrays;
/** Только опубликованные данные. TaskBinding — редактируемый клиент, не обязательный API студента. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Четыре направления";
    private static final String NORMAL_WHAT = "Сдвиг и возвращение через противоположный край.";
    private static final String NORMAL_INPUT = "Фиксированная матрица; k=1, каждое направление от исходного состояния.";
    private static final String NORMAL_EXPECTED = "Все четыре матрицы из PUBLIC01, размер 3×3.";
    private static final String BOUNDARY_NAME = "Нулевой сдвиг и полные обороты";
    private static final String BOUNDARY_WHAT = "Произвольное неотрицательное k.";
    private static final String BOUNDARY_INPUT = "Та же матрица, вправо: отдельно k=0,3,4.";
    private static final String BOUNDARY_EXPECTED = "0 и 3 сохраняют расположение; 4 соответствует сдвигу вправо на 1.";
    static int[][] input(){return new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};}
    static int[][][] one(){return new int[][][]{{{3,-3,0},{0,2,-1},{-2,1,3}},{{0,3,-3},{-1,0,2},{3,-2,1}},{{2,-1,0},{1,3,-2},{-3,0,3}},{{1,3,-2},{-3,0,3},{2,-1,0}}};}
    public static Observation observeNormal(){var actual=new StringBuilder();boolean ok=true;for(int d=0;d<4;d++){int[][] r=TaskBinding.run(input(),1,d);actual.append(new String[]{"вправо","влево","вверх","вниз"}[d]).append(": ").append(Arrays.deepToString(r)).append("; ");ok &= Arrays.deepEquals(one()[d],r);}return new Observation(actual.toString(),ok);}
    public static Observation observeBoundary(){var actual=new StringBuilder();boolean ok=true;for(int k:new int[]{0,3,4}){int[][] r=TaskBinding.run(input(),k,0);actual.append("k=").append(k).append(": ").append(Arrays.deepToString(r)).append("; ");ok &= Arrays.deepEquals(k==4?one()[0]:input(),r);}return new Observation(actual.toString(),ok);}
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
