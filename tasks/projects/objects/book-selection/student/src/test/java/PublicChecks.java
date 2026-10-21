import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
    public static final String NORMAL_NAME="Порядок и повторное вхождение";
    public static final String BOUNDARY_NAME="Равный год и отсутствие совпадений";
    public static final String REQUIREMENT="Три отбора Book, точные строки, строгий год, порядок/кратность и вход сохранены.";
    public static final String NORMAL_INPUT="[B1,B2,B1,B3] из public таблицы; Ann, Север, после2010";
    public static final String BOUNDARY_INPUT="[B3]; Ann, север, после2020";
    public static final String NORMAL_EXPECTED="Подборки B1/B2/B1; B1/B1/B3; B2/B3, вход сохранён.";
    public static final String BOUNDARY_EXPECTED="Все три подборки пусты, вход сохранён.";

    private static java.util.List<?> values(Object[] a) { return a==null?null:Arrays.stream(a).map(TaskBinding::value).toList(); }
    private static boolean unchanged(Object[] before,java.util.List<?> states,Object[] after) {
        if(before.length!=after.length)return false;
        for(int i=0;i<before.length;i++)if(before[i]!=after[i])return false;
        return states.equals(values(after));
    }

 public static Observation observeNormal() {
 Object a=TaskBinding.book(1,new String[]{"Ann","Bob"},"Север",2001),b=TaskBinding.book(2,new String[]{"Ann"},"Юг",2020),c=TaskBinding.book(3,new String[]{"Bob"},"Север",2020);
 Object[] input=TaskBinding.array(a,b,a,c),before=input.clone();var states=values(before);
 boolean okay=states.equals(List.of(List.of(1,"Первая",List.of("Ann","Bob"),"Север",2001,100,10.0,"Твёрдый"),List.of(2,"Вторая",List.of("Ann"),"Юг",2020,100,10.0,"Твёрдый"),List.of(1,"Первая",List.of("Ann","Bob"),"Север",2001,100,10.0,"Твёрдый"),List.of(3,"Третья",List.of("Bob"),"Север",2020,100,10.0,"Твёрдый")));StringBuilder actual=new StringBuilder("Исходные записи="+states+"; ");
 Object[] r0=TaskBinding.author(input,"Ann");actual.append("Отбор1=").append(values(r0)).append("; ");okay &= Objects.equals(values(r0),values(TaskBinding.array(a,b,a))) && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
 Object[] r1=TaskBinding.publisher(input,"Север");actual.append("Отбор2=").append(values(r1)).append("; ");okay &= Objects.equals(values(r1),values(TaskBinding.array(a,a,c))) && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
 Object[] r2=TaskBinding.after(input,2010);actual.append("Отбор3=").append(values(r2)).append("; ");okay &= Objects.equals(values(r2),values(TaskBinding.array(b,c))) && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
 actual.append("Вход сохранён: ").append(unchanged(before,states,input));return new Observation(actual.toString(),okay);
 }
 public static Observation observeBoundary() {
Object c=TaskBinding.book(3,new String[]{"Bob"},"Север",2020);
 Object[] input=TaskBinding.array(c),before=input.clone();var states=values(before);boolean okay=states.equals(List.of(List.of(3,"Третья",List.of("Bob"),"Север",2020,100,10.0,"Твёрдый")));StringBuilder actual=new StringBuilder("Исходные записи="+states+"; ");
Object[] r0=TaskBinding.author(input,"Ann");actual.append("Отбор1=").append(values(r0)).append("; ");okay &= r0!=null && r0.length==0 && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
Object[] r1=TaskBinding.publisher(input,"север");actual.append("Отбор2=").append(values(r1)).append("; ");okay &= r1!=null && r1.length==0 && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
Object[] r2=TaskBinding.after(input,2020);actual.append("Отбор3=").append(values(r2)).append("; ");okay &= r2!=null && r2.length==0 && unchanged(before,states,input);if(!unchanged(before,states,input))return new Observation(actual.append("Исходный массив или объекты изменены.").toString(),false);
actual.append("Вход сохранён: ").append(unchanged(before,states,input));return new Observation(actual.toString(),okay);
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
