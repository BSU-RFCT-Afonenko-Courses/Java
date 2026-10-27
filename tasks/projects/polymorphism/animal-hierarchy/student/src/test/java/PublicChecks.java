import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/** Two PUBLIC cases only. TaskBinding is an editable placement-specific client. */
public class PublicChecks {
    public record Observation(String actual, boolean passed) {}
    public static void checkNormal(Observation result) { if (!result.passed()) throw new AssertionError("Открытый обычный сценарий не совпал с ожиданием."); }
    public static void checkBoundary(Observation result) { if (!result.passed()) throw new AssertionError("Открытый граничный сценарий не совпал с ожиданием."); }
 public static final String NORMAL_NAME="Переопределённые голоса и действия";
 public static final String BOUNDARY_NAME="Одно имя у разных классов";
 public static final String REQUIREMENT="Animal→Dog→Puppy; голоса различают вид/имя; имя сохранено; equals точного класса/имени и согласованный хеш.";
 public static final String NORMAL_INPUT="Animal(Зверь),Dog(Рекс),Puppy(Луч); голос через Animal, действия Dog/Puppy";
 public static final String BOUNDARY_INPUT="Два Dog(Рекс),Puppy(Рекс),Dog(Иной),null; equals/hash/toString";
 public static final String NORMAL_EXPECTED="Вид и имя различимы, действия доступны, имена сохранены.";
 public static final String BOUNDARY_EXPECTED="Dog равны и хеши равны; Dog/Puppy в обе стороны false; иной/null false; вид/имя видны в toString.";

 public static Observation observeNormal(){
  String[] names={"Зверь","Рекс","Луч"};Object[] values={TaskBinding.animal(0,names[0]),TaskBinding.animal(1,names[1]),TaskBinding.animal(2,names[2])};StringBuilder actual=new StringBuilder();boolean okay=true;
  for(int kind=0;kind<3;kind++) {String voice=TaskBinding.voice(values[kind]);okay &= TaskBinding.voiceDescribes(kind,names[kind],voice);actual.append("Голос").append(kind).append("=").append(voice).append("; ");}
  String[] dog=TaskBinding.dogActions(values[1]),puppy=TaskBinding.actions(values[2]);actual.append("Dog действия=").append(Arrays.toString(dog)).append("; Puppy действия=").append(Arrays.toString(puppy));
  okay &= dog.length==2 && puppy.length==3;
  for(int i=0;i<dog.length;i++)okay &= TaskBinding.actionDescribes(i,names[1],dog[i]);
  for(int i=0;i<puppy.length;i++)okay &= TaskBinding.actionDescribes(i,names[2],puppy[i]);
  for(int i=0;i<3;i++)okay &= names[i].equals(TaskBinding.name(values[i]));
  actual.append("; имена=").append(Arrays.toString(Arrays.stream(values).map(TaskBinding::name).toArray()));
  return new Observation(actual.toString(),okay);
 }
 public static Observation observeBoundary(){
  Object first=TaskBinding.animal(1,new String("Рекс")),same=TaskBinding.animal(1,new String("Рекс")),puppy=TaskBinding.animal(2,"Рекс"),other=TaskBinding.animal(1,"Иной");
  boolean eq=first.equals(same),reverse=same.equals(first),cross=first.equals(puppy),crossReverse=puppy.equals(first),different=first.equals(other),nil=first.equals(null),hash=first.hashCode()==same.hashCode();String a=first.toString(),b=puppy.toString();
  boolean okay=eq&&reverse&&!cross&&!crossReverse&&!different&&!nil&&hash&&TaskBinding.voiceDescribes(1,"Рекс",a)&&TaskBinding.voiceDescribes(2,"Рекс",b);
  return new Observation("Dog/Dog="+eq+"/"+reverse+", Dog/Puppy="+cross+"/"+crossReverse+", разные="+different+",null="+nil+",хеши равных совпадают="+hash+"; toString="+a+"; "+b,okay);
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
