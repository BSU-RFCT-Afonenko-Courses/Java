/** Только два PUBLIC случая; TaskBinding — редактируемый пример, не API студента. */
public class PublicChecks {
 private static final String NORMAL_NAME="Естественный порядок и повторы";
 private static final String NORMAL_WHAT="String.compareTo и состояние данного массива.";
 private static final String NORMAL_INPUT="Массив beta,alpha,beta,Gamma; выполнить выбранную сортировку.";
 private static final String NORMAL_EXPECTED="В исходном массиве Gamma,alpha,beta,beta; обаbeta сохранены. Пузырьковый приём дополнительно по коду.";
 private static final String BOUNDARY_NAME="Пустой и уже отсортированный массивы";
 private static final String BOUNDARY_WHAT="Короткий и упорядоченный случаи.";
 private static final String BOUNDARY_INPUT="Отдельно пустой массив и Gamma,alpha,beta,beta.";
 private static final String BOUNDARY_EXPECTED="Пустой остаётся пустым; упорядоченный сохраняет последовательность. Число сравнений и identity равных строк не фиксируются.";
 public static Observation observeNormal(){String[] a={"beta","alpha","beta","Gamma"};String before=java.util.Arrays.toString(a);TaskBinding.run(a);return new Observation("до="+before+"; состояние того же массива после="+java.util.Arrays.toString(a),java.util.Arrays.equals(new String[]{"Gamma","alpha","beta","beta"},a));}
 public static Observation observeBoundary(){String[] a={},b={"Gamma","alpha","beta","beta"};TaskBinding.run(a);TaskBinding.run(b);return new Observation("пустой="+java.util.Arrays.toString(a)+"; упорядоченный="+java.util.Arrays.toString(b),a.length==0&&java.util.Arrays.equals(new String[]{"Gamma","alpha","beta","beta"},b));}
 public record Observation(String actual,boolean passed) {}
 static String shown(String s){return s==null?"null":s.isEmpty()?"Пустая строка.":PublicOutput.escaped(s);}
 public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте значение с опубликованным обычным случаем.");}
 public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте состояние с опубликованной границей.");}
 static int example(boolean normal){
  System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));
  System.out.println("Что проверяется: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));
  System.out.println("Вход и действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));
  System.out.println("Ожидаемый результат: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));
  try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());if(normal)checkNormal(o);else checkBoundary(o);System.out.println("Статус: наблюдаемое свойство соответствует; исходник, приём и объяснение оценивает преподаватель.");return 0;}
  catch(UnsupportedOperationException e){System.out.println("Статус: реализация примера ещё не завершена; проверьте исходник и привязку.");return 1;}
  catch(AssertionError e){System.out.println("Статус: неверный наблюдаемый результат; сопоставьте с опубликованным случаем.");return 1;}
  catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}
  catch(RuntimeException e){System.out.println("Статус: ошибка выполнения выбранного примера; требуется разбор реализации и привязки.");return 1;}
 }
 public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
