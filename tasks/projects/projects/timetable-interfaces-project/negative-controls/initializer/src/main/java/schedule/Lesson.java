package schedule;
import java.time.*;
public record Lesson(String id,LocalDate date,LocalTime time,String subject,String teacher,String group,String room,Kind kind,String car){public enum Kind{THEORY,PRACTICE}public Lesson room(String value){return new Lesson(id,date,time,subject,teacher,group,value,kind,car);}}
