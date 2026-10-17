import java.util.Comparator;
import grade.Student;

public final class ReportOrder implements Comparator<Student> {
    @Override public int compare(Student a, Student b) {
        int byScore = Integer.compare(b.score(), a.score());
        return byScore != 0 ? byScore : a.id().compareTo(b.id());
    }
}
