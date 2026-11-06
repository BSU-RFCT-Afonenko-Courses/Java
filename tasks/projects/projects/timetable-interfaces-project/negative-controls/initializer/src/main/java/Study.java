import java.time.*;
import java.util.*;
import java.time.LocalDate;
import java.util.List;
class Main {public static void main(String[]args){Administration manage=new SchoolSchedule(2);Queries query=(Queries)manage;manage.create();manage.createSubject("s1","Алгебра");manage.createSubject("s2","История");manage.createTeacher("t1","Иванов");manage.createTeacher("t2","Петров");manage.group("g1","9А");manage.group("g2","9Б");manage.add(new Lesson("l1",LocalDate.of(2026,10,19),LocalTime.of(9,0),"s1","t1","g1","101",Lesson.Kind.THEORY,""));manage.add(new Lesson("l2",LocalDate.of(2026,10,19),LocalTime.of(10,0),"s2","t2","g1","102",Lesson.Kind.THEORY,""));manage.add(new Lesson("l3",LocalDate.of(2026,10,20),LocalTime.of(9,0),"s1","t1","g2","101",Lesson.Kind.THEORY,""));manage.add(new Lesson("l4",LocalDate.of(2026,10,20),LocalTime.of(10,0),"s2","t2","g2","102",Lesson.Kind.THEORY,""));System.out.println(query.forTeacher("t1")+"; число="+query.teacherCount("t1"));manage.cancel("l1");manage.changeRoom("l3","C");System.out.println(query.forTeacher("t1")+"; число="+query.teacherCount("t1"));}}

public class Study {static {if(true)throw new java.lang.IllegalStateException("initializer sentinel");}
 public static Object model(int specialization){return switch(specialization){case 1->new SchoolSchedule(2);case 2->new DrivingSchedule();default->new Schedule();};}
 private static List<Object[]>entities(List<Entity>values){return values.stream().map(e->new Object[]{e.id(),e.name()}).toList();}
 private static List<Object[]>rows(List<Lesson>values){return values.stream().map(l->new Object[]{l.id(),l.date().toString(),l.time().toString(),l.subject(),l.teacher(),l.group(),l.room(),l.kind()==Lesson.Kind.THEORY?0:1,l.car()}).toList();}
 public static Object call(Object model,String operation,Object...args){Schedule s=(Schedule)model;switch(operation){
  case "createSchedule":s.create();return null;case "deleteSchedule":s.delete();return null;
  case "createSubject":s.createSubject((String)args[0],(String)args[1]);return null;case "changeSubject":s.changeSubject((String)args[0],(String)args[1]);return null;case "deleteSubject":s.deleteSubject((String)args[0]);return null;
  case "createTeacher":s.createTeacher((String)args[0],(String)args[1]);return null;case "changeTeacher":s.changeTeacher((String)args[0],(String)args[1]);return null;case "deleteTeacher":s.deleteTeacher((String)args[0]);return null;
  case "subjects":return entities(s.subjects());case "teachers":return entities(s.teachers());case "initialGroup":s.group((String)args[0],(String)args[1]);return null;
  case "addLesson":s.add(new Lesson((String)args[0],LocalDate.parse((String)args[1]),LocalTime.parse((String)args[2]),(String)args[3],(String)args[4],(String)args[5],(String)args[6],((Number)args[7]).intValue()==0?Lesson.Kind.THEORY:Lesson.Kind.PRACTICE,(String)args[8]));return null;
  case "cancelLesson":s.cancel((String)args[0]);return null;case "changeRoom":s.changeRoom((String)args[0],(String)args[1]);return null;
  case "teacherLessons":return rows(s.forTeacher((String)args[0]));case "groupLessons":return rows(s.forGroup((String)args[0]));case "teacherCount":return s.teacherCount((String)args[0]);case "groupCount":return s.groupCount((String)args[0]);
  case "dayOff":s.dayOff(LocalDate.parse((String)args[0]));return null;case "classLessons":return rows(((SchoolSchedule)s).forClass((String)args[0]));case "lessonsPerDayValid":return ((SchoolSchedule)s).withinLimit((String)args[0],LocalDate.parse((String)args[1]));
  case "setCarAvailable":((DrivingSchedule)s).car((String)args[0],(Boolean)args[1]);return null;case "practicalCarValid":return ((DrivingSchedule)s).validPractice((String)args[0]);default:throw new IllegalArgumentException("Неизвестная операция: "+operation);
 }}
}

interface Administration {void create();void delete();void createSubject(String id,String name);void changeSubject(String id,String name);void deleteSubject(String id);void createTeacher(String id,String name);void changeTeacher(String id,String name);void deleteTeacher(String id);void group(String id,String name);void add(Lesson lesson);void cancel(String id);void changeRoom(String id,String room);void dayOff(LocalDate day);}

interface DrivingQueries extends Queries {void car(String id,boolean available);boolean validPractice(String lessonId);}

class DrivingSchedule extends Schedule implements DrivingQueries {private final Set<String>cars=new HashSet<>();public void car(String id,boolean available){if(available)cars.add(id);else cars.remove(id);}public boolean validPractice(String lessonId){Lesson l=lessons.get(lessonId);return l!=null&&(l.kind()==Lesson.Kind.THEORY||cars.contains(l.car()));}}

record Entity(String id,String name){}

record Lesson(String id,LocalDate date,LocalTime time,String subject,String teacher,String group,String room,Kind kind,String car){public enum Kind{THEORY,PRACTICE}public Lesson room(String value){return new Lesson(id,date,time,subject,teacher,group,value,kind,car);}}

interface Queries {List<Entity>subjects();List<Entity>teachers();List<Lesson>forTeacher(String id);List<Lesson>forGroup(String id);int teacherCount(String id);int groupCount(String id);}

class Schedule implements Administration,Queries {
 private boolean active;private final Map<String,Entity>subjects=new LinkedHashMap<>(),teachers=new LinkedHashMap<>(),groups=new LinkedHashMap<>();protected final Map<String,Lesson>lessons=new LinkedHashMap<>();private final Set<LocalDate>daysOff=new HashSet<>();
 protected void active(){if(!active)throw new IllegalStateException("Расписание не создано");}
 public void create(){active=true;}public void delete(){active=false;subjects.clear();teachers.clear();groups.clear();lessons.clear();daysOff.clear();}
 public void createSubject(String id,String name){active();subjects.put(id,new Entity(id,name));}public void changeSubject(String id,String name){active();subjects.put(id,new Entity(id,name));}public void deleteSubject(String id){active();subjects.remove(id);lessons.values().removeIf(l->l.subject().equals(id));}
 public void createTeacher(String id,String name){active();teachers.put(id,new Entity(id,name));}public void changeTeacher(String id,String name){active();teachers.put(id,new Entity(id,name));}public void deleteTeacher(String id){active();teachers.remove(id);lessons.values().removeIf(l->l.teacher().equals(id));}
 public void group(String id,String name){active();groups.put(id,new Entity(id,name));}
 public void add(Lesson lesson){active();if(!subjects.containsKey(lesson.subject())||!teachers.containsKey(lesson.teacher())||!groups.containsKey(lesson.group())||daysOff.contains(lesson.date()))throw new IllegalArgumentException("Занятие ссылается на недоступные данные или выходной");lessons.put(lesson.id(),lesson);}
 public void cancel(String id){active();lessons.remove(id);}public void changeRoom(String id,String room){active();lessons.computeIfPresent(id,(key,lesson)->lesson.room(room));}
 public void dayOff(LocalDate day){active();daysOff.add(day);lessons.values().removeIf(l->l.date().equals(day));}
 public List<Entity>subjects(){return List.copyOf(subjects.values());}public List<Entity>teachers(){return List.copyOf(teachers.values());}
 public List<Lesson>forTeacher(String id){return lessons.values().stream().filter(l->l.teacher().equals(id)).toList();}public List<Lesson>forGroup(String id){return lessons.values().stream().filter(l->l.group().equals(id)).toList();}
 public int teacherCount(String id){return forTeacher(id).size();}public int groupCount(String id){return forGroup(id).size();}
}

interface SchoolQueries extends Queries {List<Lesson>forClass(String id);boolean withinLimit(String id,LocalDate day);}

class SchoolSchedule extends Schedule implements SchoolQueries {private final int maximum;public SchoolSchedule(int maximum){this.maximum=maximum;}public List<Lesson>forClass(String id){return forGroup(id);}public boolean withinLimit(String id,LocalDate day){return forClass(id).stream().filter(l->l.date().equals(day)).count()<=maximum;}}
