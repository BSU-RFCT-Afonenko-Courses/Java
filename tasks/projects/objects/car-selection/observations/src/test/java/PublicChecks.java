import java.lang.AssertionError;
import java.lang.Object;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Два опубликованных наблюдения; внутренние имена решения свободны. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
    public static final String NORMAL_NAME="Три отбора с сохранением повторов";
    public static final String BOUNDARY_NAME="Равные возраст и цена";
    public static final String REQUIREMENT="Три отбора Car, параметр текущего года, строгие возраст/цена, порядок/кратность и вход сохранены.";
    public static final String NORMAL_INPUT="[C1,C2,C1,C3], now2025; Вектор, M1 возраст>5, год2010 цена>5";
    public static final String BOUNDARY_INPUT="[C4], now2025; вектор, M1 возраст>5, год2020 цена>10";
    public static final String NORMAL_EXPECTED="Подборки C1/C2/C1; C1/C2/C1; C1/C1, вход сохранён.";
    public static final String BOUNDARY_EXPECTED="Все три подборки пусты, вход сохранён.";

    private static List<?> values(Object[] a) { return a==null?null:Arrays.stream(a).map(TaskBinding::value).toList(); }
    private static boolean unchanged(Object[] before,List<?> states,Object[] after) {
        if(before.length!=after.length)return false;
        for(int i=0;i<before.length;i++)if(before[i]!=after[i])return false;
        return states.equals(values(after));
    }

 public static Observation observeNormal() {
 Object a=TaskBinding.car(1,"Вектор","M1",2010,10),b=TaskBinding.car(2,"Вектор","M1",2012,20),c=TaskBinding.car(3,"Линия","M2",2020,30);
 Object[] input=TaskBinding.array(a,b,a,c),before=input.clone();var states=values(before);
 boolean okay=states.equals(List.of(List.of(1L,"Вектор","M1",2010L,"Синий",10.0,"A1"),List.of(2L,"Вектор","M1",2012L,"Синий",20.0,"A2"),List.of(1L,"Вектор","M1",2010L,"Синий",10.0,"A1"),List.of(3L,"Линия","M2",2020L,"Синий",30.0,"A3")));StringBuilder actual=new StringBuilder("Исходные записи="+states+"; ");
 Object[] r0=TaskBinding.make(input,"Вектор");actual.append("Отбор1=").append(values(r0)).append("; ");okay &= Objects.equals(values(r0),values(TaskBinding.array(a,b,a))) && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
 Object[] r1=TaskBinding.older(input,"M1",2025,5);actual.append("Отбор2=").append(values(r1)).append("; ");okay &= Objects.equals(values(r1),values(TaskBinding.array(a,b,a))) && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
 Object[] r2=TaskBinding.dearer(input,2010,5);actual.append("Отбор3=").append(values(r2)).append("; ");okay &= Objects.equals(values(r2),values(TaskBinding.array(a,a))) && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
 actual.append("Вход сохранён: ").append(unchanged(before,states,input));return new Observation(actual.toString(),okay);
 }
 public static Observation observeBoundary() {
Object c=TaskBinding.car(4,"Вектор","M1",2020,10);
 Object[] input=TaskBinding.array(c),before=input.clone();var states=values(before);boolean okay=states.equals(List.of(List.of(4L,"Вектор","M1",2020L,"Синий",10.0,"A4")));StringBuilder actual=new StringBuilder("Исходные записи="+states+"; ");
Object[] r0=TaskBinding.make(input,"вектор");actual.append("Отбор1=").append(values(r0)).append("; ");okay &= r0!=null && r0.length==0 && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
Object[] r1=TaskBinding.older(input,"M1",2025,5);actual.append("Отбор2=").append(values(r1)).append("; ");okay &= r1!=null && r1.length==0 && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
Object[] r2=TaskBinding.dearer(input,2020,10);actual.append("Отбор3=").append(values(r2)).append("; ");okay &= r2!=null && r2.length==0 && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
actual.append("Вход сохранён: ").append(unchanged(before,states,input));return new Observation(actual.toString(),okay);
 }


}
