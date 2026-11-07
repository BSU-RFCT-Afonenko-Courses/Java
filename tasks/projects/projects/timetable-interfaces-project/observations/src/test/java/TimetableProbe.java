import java.lang.ArithmeticException;
import java.lang.AssertionError;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.Boolean;
import java.lang.Number;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class TimetableProbe {
 record Lesson(String id,String date,String time,String subject,String teacher,String group,String room,int kind,String car){}
 static Object call(Object model,String op,Object...args){return Study.call(model,op,args);}
 static int exactInteger(Number value){
  try{
   if(value instanceof BigDecimal decimal)return new BigDecimal(new BigInteger(decimal.unscaledValue().toByteArray()),decimal.scale()).intValueExact();
   if(value instanceof BigInteger integer)return new BigInteger(integer.toByteArray()).intValueExact();
   double original=value.doubleValue();
   if(!Double.isFinite(original)||original!=Math.rint(original)||original<Integer.MIN_VALUE||original>Integer.MAX_VALUE)
    throw new AssertionError("Ожидалось точное целое числовое значение: "+original);
   int exact=value.intValue();
   if((double)exact!=original)throw new AssertionError("Числовые проекции целого значения расходятся");
   return exact;
  }catch(ArithmeticException failure){throw new AssertionError("Числовое значение нельзя сузить без потерь",failure);}
 }
 static int count(Object model,String op,String id){return exactInteger((Number)call(model,op,id));}
 static List<Lesson>lessons(Object model,String op,String id){List<Lesson>snapshot=new ArrayList<>();for(Object raw:(List<?>)call(model,op,id)){Object[]row=(Object[])raw;snapshot.add(new Lesson((String)row[0],(String)row[1],(String)row[2],(String)row[3],(String)row[4],(String)row[5],(String)row[6],exactInteger((Number)row[7]),(String)row[8]));}return List.copyOf(snapshot);}
 static Map<String,String>names(Object model,String op){Map<String,String>snapshot=new HashMap<>();for(Object raw:(List<?>)call(model,op)){Object[]row=(Object[])raw;assertNull(snapshot.put((String)row[0],(String)row[1]));}return Map.copyOf(snapshot);}
 // Полные detached записи сравниваются с кратностью, порядок выборки свободен.
 static Map<Lesson,Long> multiset(List<Lesson> snapshot){Map<Lesson,Long> counts=new HashMap<>();for(Lesson lesson:snapshot)counts.merge(lesson,1L,Long::sum);return Map.copyOf(counts);}
 static Set<String>ids(List<Lesson>lessons){Set<String>ids=new HashSet<>();for(Lesson l:lessons)assertTrue(ids.add(l.id()));return ids;}
 static void lesson(Object m,String id,int day,int hour,String subject,String teacher,String group,String room,int kind,String car){call(m,"addLesson",id,"2026-10-"+day,String.format("%02d:00",hour),subject,teacher,group,room,kind,car);}
 static Object fixture(int kind){Object m=Study.model(kind);call(m,"createSchedule");call(m,"createSubject","s1","Math");call(m,"createSubject","s2","History");call(m,"createTeacher","t1","One");call(m,"createTeacher","t2","Two");call(m,"initialGroup","g1","9A");call(m,"initialGroup","g2","9B");lesson(m,"l1",19,9,"s1","t1","g1","101",0,"");lesson(m,"l2",19,10,"s2","t2","g1","102",0,"");lesson(m,"l3",20,9,"s1","t1","g2","101",0,"");lesson(m,"l4",20,10,"s2","t2","g2","102",0,"");assertEquals(Map.of("s1","Math","s2","History"),names(m,"subjects"));assertEquals(Map.of("t1","One","t2","Two"),names(m,"teachers"));assertEquals(Set.of(new Lesson("l1","2026-10-19","09:00","s1","t1","g1","101",0,""),new Lesson("l3","2026-10-20","09:00","s1","t1","g2","101",0,"")),new HashSet<>(lessons(m,"teacherLessons","t1")));assertEquals(Set.of(new Lesson("l2","2026-10-19","10:00","s2","t2","g1","102",0,""),new Lesson("l4","2026-10-20","10:00","s2","t2","g2","102",0,"")),new HashSet<>(lessons(m,"teacherLessons","t2")));return m;}
 static String normal(){Object m=fixture(0);List<Lesson>before=lessons(m,"teacherLessons","t1");assertEquals(Set.of("l1","l3"),ids(before));assertEquals(2,count(m,"teacherCount","t1"));assertEquals(Set.of("l1","l2"),ids(lessons(m,"groupLessons","g1")));assertEquals(2,count(m,"groupCount","g1"));Lesson third=before.stream().filter(l->l.id().equals("l3")).findFirst().orElseThrow();call(m,"cancelLesson","l1");assertEquals(Set.of("l3"),ids(lessons(m,"teacherLessons","t1")));assertEquals(1,count(m,"teacherCount","t1"));assertEquals(Set.of("l2"),ids(lessons(m,"groupLessons","g1")));assertEquals(1,count(m,"groupCount","g1"));call(m,"changeRoom","l3","C");assertEquals(List.of(new Lesson(third.id(),third.date(),third.time(),third.subject(),third.teacher(),third.group(),"C",third.kind(),third.car())),lessons(m,"teacherLessons","t1"));return lessons(m,"teacherLessons","t1").toString();}
 static String boundary(){Object deleted=fixture(0);Map<String,String>before=names(deleted,"teachers");List<Lesson>previous=lessons(deleted,"teacherLessons","t1");boolean refused=false;try{Object result=call(deleted,"deleteTeacher","t1");refused=Boolean.FALSE.equals(result);}catch(RuntimeException failure){if(!TimetablePolicy.DELETION.equals("reject"))throw failure;refused=true;}
  if(TimetablePolicy.DELETION.equals("reject")){assertTrue(refused);assertEquals(before,names(deleted,"teachers"));assertEquals(multiset(previous),multiset(lessons(deleted,"teacherLessons","t1")));assertEquals(2,count(deleted,"teacherCount","t1"));}else if(TimetablePolicy.DELETION.equals("retain-lessons")){assertFalse(names(deleted,"teachers").containsKey("t1"));assertEquals(multiset(previous),multiset(lessons(deleted,"teacherLessons","t1")));assertEquals(2,count(deleted,"teacherCount","t1"));assertEquals(Set.of("l2","l4"),ids(lessons(deleted,"teacherLessons","t2")));}else{assertFalse(names(deleted,"teachers").containsKey("t1"));assertEquals(List.of(),lessons(deleted,"teacherLessons","t1"));assertEquals(0,count(deleted,"teacherCount","t1"));assertEquals(Set.of("l2","l4"),ids(lessons(deleted,"teacherLessons","t2")));}
  Object holiday=fixture(0);call(holiday,"dayOff","2026-10-20");assertEquals(Set.of("l1"),ids(lessons(holiday,"teacherLessons","t1")));assertEquals(Set.of("l2"),ids(lessons(holiday,"teacherLessons","t2")));assertEquals(1,count(holiday,"teacherCount","t1"));assertEquals(0,count(holiday,"groupCount","g2"));
  Object school=fixture(1);for(int i=2;i<TimetablePolicy.SCHOOL_LIMIT;i++)lesson(school,"limit"+i,19,10+i,"s1","t1","g1","C",0,"");assertEquals(TimetablePolicy.SCHOOL_LIMIT,lessons(school,"classLessons","g1").stream().filter(l->l.date().equals("2026-10-19")).count());assertEquals(Boolean.TRUE,call(school,"lessonsPerDayValid","g1","2026-10-19"));boolean addRefused=false;try{Object result=call(school,"addLesson","excess","2026-10-19","18:00","s1","t1","g1","C",0,"");addRefused=Boolean.FALSE.equals(result);}catch(RuntimeException failure){addRefused=true;}if(addRefused){assertFalse(ids(lessons(school,"classLessons","g1")).contains("excess"));assertEquals(Boolean.TRUE,call(school,"lessonsPerDayValid","g1","2026-10-19"));}else assertEquals(Boolean.FALSE,call(school,"lessonsPerDayValid","g1","2026-10-19"));
  Object driving=fixture(2);call(driving,"setCarAvailable","c1",true);lesson(driving,"p1",19,11,"s1","t1","g1","Road",1,"c1");assertEquals(Boolean.TRUE,call(driving,"practicalCarValid","p1"));assertEquals(1,lessons(driving,"groupLessons","g1").stream().filter(l->l.kind()==1).count());assertEquals(2,lessons(driving,"groupLessons","g1").stream().filter(l->l.kind()==0).count());Object missing=fixture(2);call(missing,"setCarAvailable","absent",false);boolean rejected=false;try{Object result=call(missing,"addLesson","p2","2026-10-19","11:00","s1","t1","g1","Road",1,"absent");rejected=Boolean.FALSE.equals(result);}catch(RuntimeException failure){rejected=true;}if(rejected)assertFalse(ids(lessons(missing,"groupLessons","g1")).contains("p2"));else assertEquals(Boolean.FALSE,call(missing,"practicalCarValid","p2"));return "Удаление/выходной/предел/автомобиль наблюдены на независимых моделях";
 }

}
