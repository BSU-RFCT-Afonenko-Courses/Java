package schedule;import java.util.List;import java.time.LocalDate;public interface SchoolQueries extends Queries {List<Lesson>forClass(String id);boolean withinLimit(String id,LocalDate day);}
