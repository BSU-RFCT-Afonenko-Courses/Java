package schedule;
import java.lang.String;
import schedule.Lesson;
import schedule.Schedule;
import schedule.SchoolQueries;
import java.util.List;import java.time.LocalDate;public class SchoolSchedule extends Schedule implements SchoolQueries {private final int maximum;public SchoolSchedule(int maximum){this.maximum=maximum;}public List<Lesson>forClass(String id){return forGroup(id);}public boolean withinLimit(String id,LocalDate day){return forClass(id).stream().filter(l->l.date().equals(day)).count()<=maximum;}}
