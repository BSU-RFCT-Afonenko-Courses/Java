package schedule;
import java.util.*;import java.time.LocalDate;
public class Schedule implements Administration,Queries {
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
