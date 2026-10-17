package grade;
import java.util.*;
public final class Reports {
 private Reports(){}
 public static List<Student> selected(List<Student> students,int threshold) {
  if(threshold<0||threshold>100)throw new IllegalArgumentException("порог");
  List<Student> result=new ArrayList<>();
  for(Student s:students)if(s.score()>=threshold)result.add(s);
  result.sort(Comparator.comparingInt(Student::score).reversed().thenComparing(Student::id));
  return Collections.unmodifiableList(result);
 }
 public static String line(Student s){return String.join(";",s.id(),s.name(),Integer.toString(s.score()));}
}
