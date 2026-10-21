import java.util.*;import java.math.BigInteger;
/** Только два опубликованных наблюдения; клиент TaskBinding адаптируется к выбранному API. */
public class PublicChecks {
 public record Observation(String actual,boolean passed){}
 public static void checkNormal(Observation r){if(!r.passed())throw new AssertionError("Обычный открытый сценарий требует исправления.");}
 public static void checkBoundary(Observation r){if(!r.passed())throw new AssertionError("Граничный открытый сценарий требует исправления.");}
public static final String NORMAL_NAME="Арифметика и сопряжённые значения";
public static final String BOUNDARY_NAME="Сумма и произведение пустого массива";
public static final String NORMAL_INPUT="a=1+2i,b=3-4i; массив[1+i,1-i]";
public static final String BOUNDARY_INPUT="Пустой массив";
public static final String NORMAL_EXPECTED="4-2i;-2+6i;11+2i;-0.2+0.4i; сумма/произведение2+0i; вход сохранён";
public static final String BOUNDARY_EXPECTED="Сумма0+0i, произведение1+0i";
static boolean eq(double[]a,double r,double i){return a.length==2&&Double.isFinite(a[0])&&Double.isFinite(a[1])&&Math.abs(a[0]-r)<=1e-8&&Math.abs(a[1]-i)<=1e-8;}
public static Observation observeNormal(){double[][]e={{4,-2},{-2,6},{11,2},{-.2,.4}};boolean ok=true;StringBuilder actual=new StringBuilder("Операции=");for(int op=0;op<4;op++){double[]p=TaskBinding.parts(TaskBinding.operation(TaskBinding.value(1,2),TaskBinding.value(3,-4),op));actual.append(Arrays.toString(p)).append("; ");ok &= eq(p,e[op][0],e[op][1]);}Object[]a=TaskBinding.array(TaskBinding.value(1,1),TaskBinding.value(1,-1));double[]s=TaskBinding.parts(TaskBinding.sum(a));boolean afterSum=eq(TaskBinding.parts(a[0]),1,1)&&eq(TaskBinding.parts(a[1]),1,-1);double[]p=TaskBinding.parts(TaskBinding.product(a));boolean kept=afterSum&&eq(TaskBinding.parts(a[0]),1,1)&&eq(TaskBinding.parts(a[1]),1,-1);return new Observation(actual+"Сумма="+Arrays.toString(s)+"; произведение="+Arrays.toString(p)+"; вход сохранён="+kept,ok&&eq(s,2,0)&&eq(p,2,0)&&kept);}
public static Observation observeBoundary(){Object[]a=TaskBinding.array();double[]s=TaskBinding.parts(TaskBinding.sum(a)),p=TaskBinding.parts(TaskBinding.product(a));return new Observation("Сумма="+Arrays.toString(s)+"; произведение="+Arrays.toString(p),eq(s,0,0)&&eq(p,1,0)&&a.length==0);}
public static void main(String[]args){boolean okay=true;for(boolean normal:new boolean[]{true,false}){System.out.println("Проверка: "+(normal?NORMAL_NAME:BOUNDARY_NAME));System.out.println("Вход/действие: "+(normal?NORMAL_INPUT:BOUNDARY_INPUT));System.out.println("Ожидается: "+(normal?NORMAL_EXPECTED:BOUNDARY_EXPECTED));try{Observation r=normal?observeNormal():observeBoundary();System.out.println("Получено: "+r.actual());System.out.println("Статус: "+(r.passed()?"соответствует открытому ожиданию":"требует исправления; сопоставьте значения и состояние с условием"));okay &= r.passed();}catch(Throwable t){System.out.println("Получено: операция завершилась исключением "+t.getClass().getSimpleName());System.out.println("Статус: ошибка выполнения; проверьте операцию и соответствие клиента API решения.");okay=false;}}if(!okay)System.exit(1);}}
