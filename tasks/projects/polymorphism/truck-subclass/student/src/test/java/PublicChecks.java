import java.util.*;
public class PublicChecks {
public record Observation(String actual,boolean passed){}
static final String NORMAL_NAME="Унаследованные характеристики";
static final String NORMAL_WHAT="Дальность и расход Vehicle/Truck и грузоподъёмность.";
static final String NORMAL_INPUT="Vehicle(2,10,25), Truck(2,10,25,100); range, fuelNeeded(50), getCargo";
static final String NORMAL_EXPECTED="250 и 2.0 у обоих; cargo100; абсолютная погрешность 1e-6.";
static final String BOUNDARY_NAME="Отказ до записи";
static final String BOUNDARY_WHAT="Три IllegalArgumentException и прежнее состояние после отказов.";
static final String BOUNDARY_INPUT="Vehicle.setFuelcap(0), fuelNeeded(-1); Truck.putCargo(-1)";
static final String BOUNDARY_EXPECTED="Все три IllegalArgumentException; базовое состояние2,10,25/range250, cargo100.";
static String lf(String s){return s.replace("\r\n","\n");}
public static Observation observeNormal(){Object v=TaskBinding.vehicle(2,10,25),t=TaskBinding.truck(2,10,25,100);int vr=TaskBinding.range(v),tr=TaskBinding.range(t),cargo=TaskBinding.cargo(t);double vf=TaskBinding.fuel(v,50),tf=TaskBinding.fuel(t,50);return new Observation("Vehicle: дальность="+vr+", топливо="+vf+"; Truck: дальность="+tr+", топливо="+tf+", грузоподъёмность="+cargo,vr==250&&tr==250&&Math.abs(vf-2)<=1e-6&&Math.abs(tf-2)<=1e-6&&cargo==100);}
public static Observation observeBoundary(){Object v=TaskBinding.vehicle(2,10,25),t=TaskBinding.truck(2,10,25,100);boolean a=false,b=false,c=false;try{TaskBinding.setFuel(v,0);}catch(IllegalArgumentException e){a=true;}try{TaskBinding.fuel(v,-1);}catch(IllegalArgumentException e){b=true;}try{TaskBinding.cargo(t,-1);}catch(IllegalArgumentException e){c=true;}int[] vs=TaskBinding.state(v),ts=TaskBinding.state(t);int range=TaskBinding.range(v),cargo=TaskBinding.cargo(t);return new Observation("IllegalArgumentException="+a+","+b+","+c+"; Vehicle="+Arrays.toString(vs)+", дальность="+range+"; Truck="+Arrays.toString(ts)+", груз="+cargo,a&&b&&c&&range==250&&cargo==100&&Arrays.equals(vs,new int[]{2,10,25})&&Arrays.equals(ts,new int[]{2,10,25}));}
public static void checkNormal(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте обычное наблюдение с условием.");}
public static void checkBoundary(Observation o){if(!o.passed())throw new AssertionError("Сопоставьте опубликованную границу с условием.");}
static int example(boolean normal){System.out.println("Сценарий: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Что наблюдаем: "+(normal?NORMAL_WHAT:BOUNDARY_WHAT));System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));try{var o=normal?observeNormal():observeBoundary();System.out.println("Получено: "+o.actual());System.out.println(o.passed()?"Статус: наблюдаемое свойство соответствует; исходник и объяснение оценивает преподаватель.":"Статус: наблюдаемое свойство не соответствует.");return o.passed()?0:1;}catch(LinkageError e){System.out.println("Статус: привязка или среда не подготовлена; требуется ручная проверка, оценки нет.");return 2;}catch(RuntimeException e){System.out.println("Статус: операция или пример привязки не завершены корректно. Тип: "+e.getClass().getSimpleName());return 1;}catch(Error e){System.out.println("Статус: ошибка выполнения операции. Тип: "+e.getClass().getSimpleName());return 1;}}
public static void main(String[] args){int status=Math.max(example(true),example(false));if(status!=0)System.exit(status);}
}
