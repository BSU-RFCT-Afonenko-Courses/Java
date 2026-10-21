import java.util.*;public interface DrivingAPI{List<Schedule.Lesson>theory(String group);List<Schedule.Lesson>practice(String group);boolean carAvailable(String id);}
