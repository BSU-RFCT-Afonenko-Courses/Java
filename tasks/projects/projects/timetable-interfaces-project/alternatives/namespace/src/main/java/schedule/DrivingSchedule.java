package schedule;
import java.lang.String;
import java.util.HashSet;
import java.util.Set;
import schedule.DrivingQueries;
import schedule.Lesson;
import schedule.Schedule;
import java.util.*;public class DrivingSchedule extends Schedule implements DrivingQueries {private final Set<String>cars=new HashSet<>();public void car(String id,boolean available){if(available)cars.add(id);else cars.remove(id);}public boolean validPractice(String lessonId){Lesson l=lessons.get(lessonId);return l!=null&&(l.kind()==Lesson.Kind.THEORY||cars.contains(l.car()));}}
