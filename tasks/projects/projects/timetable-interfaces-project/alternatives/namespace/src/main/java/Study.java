import java.lang.Boolean;
import java.lang.IllegalArgumentException;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import schedule.DrivingSchedule;
import schedule.Entity;
import schedule.Lesson;
import schedule.Schedule;
import schedule.SchoolSchedule;
import schedule.*;import java.time.*;import java.util.*;
public class Study {
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
