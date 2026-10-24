import java.util.Arrays;
/** Только опубликованные данные. TaskBinding — редактируемый клиент, не обязательный API студента. */
public class PublicChecks {
    private static final String NORMAL_NAME = "Три угла против часовой стрелки";
    private static final String NORMAL_WHAT = "Направление и угол поворота.";
    private static final String NORMAL_INPUT = "Фиксированная матрица; 90,180,270 относительно исходной.";
    private static final String NORMAL_EXPECTED = "Три точные матрицы PUBLIC01.";
    private static final String BOUNDARY_NAME = "Исходное состояние после поворотов";
    private static final String BOUNDARY_WHAT = "Наблюдение сохранённого объекта после вычислений.";
    private static final String BOUNDARY_INPUT = "Та же исходная матрица после трёх поворотов.";
    private static final String BOUNDARY_EXPECTED = "Исходные значения и позиции прежние; каждый результат — новый массив. Создание дополнительно проследить по коду.";
    static int[][] input(){return new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};}
    static int[][][] turns(){return new int[][][]{{{3,0,-2},{0,-1,3},{-3,2,1}},{{-2,3,1},{0,-1,2},{3,0,-3}},{{1,2,-3},{3,-1,0},{-2,0,3}}};}
    public static Observation observeNormal(){var actual=new StringBuilder();boolean ok=true;int[][] a=input();int i=0;for(int angle:new int[]{90,180,270}){int[][] r=TaskBinding.run(a,angle);actual.append(angle).append(": ").append(Arrays.deepToString(r)).append("; ");ok &= Arrays.deepEquals(turns()[i++],r);}return new Observation(actual.toString(),ok);}
    public static Observation observeBoundary(){int[][] a=input();boolean ok=true;var actual=new StringBuilder();for(int angle:new int[]{90,180,270}){int[][] r=TaskBinding.run(a,angle);boolean fresh=r!=null&&r!=a;actual.append(angle).append(": новый внешний массив=").append(fresh).append(", исходник=").append(Arrays.deepToString(a)).append("; ");ok &= fresh&&Arrays.deepEquals(input(),a);}return new Observation(actual.toString(),ok);}
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
