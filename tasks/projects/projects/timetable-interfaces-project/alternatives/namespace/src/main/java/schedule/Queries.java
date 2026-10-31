package schedule;
import java.lang.String;
import schedule.Entity;
import schedule.Lesson;
import java.util.List;
public interface Queries {List<Entity>subjects();List<Entity>teachers();List<Lesson>forTeacher(String id);List<Lesson>forGroup(String id);int teacherCount(String id);int groupCount(String id);}
