package schedule;
import java.lang.String;
import schedule.Queries;
public interface DrivingQueries extends Queries {void car(String id,boolean available);boolean validPractice(String lessonId);}
