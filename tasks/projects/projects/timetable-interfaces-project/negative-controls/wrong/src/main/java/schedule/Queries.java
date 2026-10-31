package schedule;import java.util.List;
public interface Queries {List<Entity>subjects();List<Entity>teachers();List<Lesson>forTeacher(String id);List<Lesson>forGroup(String id);int teacherCount(String id);int groupCount(String id);}
