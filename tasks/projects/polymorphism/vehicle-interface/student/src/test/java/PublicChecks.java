import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
 public static final String NORMAL_NAME="Чтение и допустимое изменение характеристик";
 public static final String BOUNDARY_NAME="Отказ изменения и отрицательного расстояния";
 public static final String REQUIREMENT="Чтение/изменение через IVehicle, вещественное топливо, отказ до записи с сохранением состояния.";
 public static final String NORMAL_INPUT="Vehicle(2,10,25):50миль; затем setters(0,12,30),90миль";
 public static final String BOUNDARY_INPUT="Новый Vehicle(2,10,25):setFuelcap(0),fuelNeeded(-1),fuelNeeded(0)";
 public static final String NORMAL_EXPECTED="(2,10,25),250,2.0; затем(0,12,30),360,3.0; допуск1e-6.";
 public static final String BOUNDARY_EXPECTED="Два IllegalArgumentException; прежние(2,10,25),range250; fuelNeeded(0)=0.0.";

 private static boolean near(double value,double expected){return Double.isFinite(value)&&Math.abs(value-expected)<=1e-6;}
 public static Observation observeNormal(){
  Object value=TaskBinding.vehicle(2,10,25);int[] before=TaskBinding.state(value);int firstRange=TaskBinding.range(value);double firstFuel=TaskBinding.fuel(value,50);
  TaskBinding.set(value,0,0);TaskBinding.set(value,1,12);TaskBinding.set(value,2,30);int[] after=TaskBinding.state(value);int lastRange=TaskBinding.range(value);double lastFuel=TaskBinding.fuel(value,90);
  return new Observation("До="+Arrays.toString(before)+",range="+firstRange+",fuel="+firstFuel+"; после="+Arrays.toString(after)+",range="+lastRange+",fuel="+lastFuel,Arrays.equals(before,new int[]{2,10,25})&&firstRange==250&&near(firstFuel,2)&&Arrays.equals(after,new int[]{0,12,30})&&lastRange==360&&near(lastFuel,3));
 }
 public static Observation observeBoundary(){
  Object value=TaskBinding.vehicle(2,10,25);boolean setterRejected=false,milesRejected=false;
  try{TaskBinding.set(value,1,0);}catch(IllegalArgumentException expected){setterRejected=true;}
  int[] state=TaskBinding.state(value);int range=TaskBinding.range(value);
  try{TaskBinding.fuel(value,-1);}catch(IllegalArgumentException expected){milesRejected=true;}
  double zero=TaskBinding.fuel(value,0);
  return new Observation("setFuelcap(0) отказ="+setterRejected+",state="+Arrays.toString(state)+",range="+range+"; отрицательные мили отказ="+milesRejected+",fuel(0)="+zero,setterRejected&&milesRejected&&Arrays.equals(state,new int[]{2,10,25})&&range==250&&near(zero,0));
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
            System.out.println("Статус: " + (result.passed()?"наблюдаемое поведение совпало; обязательная ручная часть остаётся":"требует исправления"));
            success &= result.passed();
        }
        if (!success) System.exit(1);
    }
}
