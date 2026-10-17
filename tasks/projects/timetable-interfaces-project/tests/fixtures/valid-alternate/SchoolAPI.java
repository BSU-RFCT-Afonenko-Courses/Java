package alternate;import java.time.*;import java.util.*;public interface SchoolAPI{List<Schedule.Lesson>forClass(String group);boolean withinDailyLimit(String group,LocalDate day);}
