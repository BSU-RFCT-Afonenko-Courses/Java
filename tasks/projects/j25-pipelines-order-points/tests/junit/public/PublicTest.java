import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
static java.util.List<String> tokens(String s){return s.isBlank()?java.util.List.of():java.util.List.of(s.trim().split("\\s+"));}
@Test void publicDistances(){assertEquals(java.util.List.of("(2:0)","(1:10)"),tokens(TaskBinding.run(new int[][]{{1,10},{2,0}})));assertEquals(java.util.List.of("(3:4)","(0:5)"),tokens(TaskBinding.run(new int[][]{{3,4},{0,5}})));}
}
