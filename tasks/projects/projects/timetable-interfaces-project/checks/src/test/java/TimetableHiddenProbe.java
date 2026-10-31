import java.lang.Boolean;
import java.lang.Number;
import java.lang.Object;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class TimetableHiddenProbe extends TimetableProbe {static void remaining(){Object m=fixture(0);call(m,"createSubject","s3","Three");call(m,"createTeacher","t3","Three");assertEquals("Three",names(m,"subjects").get("s3"));assertEquals("Three",names(m,"teachers").get("t3"));call(m,"changeSubject","s3","Changed subject");call(m,"changeTeacher","t3","Changed teacher");assertEquals("Changed subject",names(m,"subjects").get("s3"));assertEquals("Changed teacher",names(m,"teachers").get("t3"));call(m,"deleteSubject","s3");call(m,"deleteTeacher","t3");assertEquals(Set.of("s1","s2"),names(m,"subjects").keySet());assertEquals(Set.of("t1","t2"),names(m,"teachers").keySet());lesson(m,"l5",21,12,"s1","t1","g1","D",0,"");assertEquals(Set.of("l1","l3","l5"),ids(lessons(m,"teacherLessons","t1")));assertEquals(3,count(m,"teacherCount","t1"));assertEquals(3,count(m,"groupCount","g1"));call(m,"cancelLesson","l5");assertEquals(2,count(m,"teacherCount","t1"));assertEquals(Set.of("l1","l3"),ids(lessons(m,"teacherLessons","t1")));call(m,"deleteSchedule");for(String id:List.of("t1","t2")){assertEquals(List.of(),lessons(m,"teacherLessons",id));assertEquals(0,count(m,"teacherCount",id));}call(m,"createSchedule");call(m,"createSubject","newS","New subject");call(m,"createTeacher","newT","New teacher");call(m,"initialGroup","newG","New group");lesson(m,"newL",22,8,"newS","newT","newG","E",0,"");assertEquals(Set.of("newL"),ids(lessons(m,"teacherLessons","newT")));assertEquals(1,count(m,"groupCount","newG"));}}
