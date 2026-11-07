import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void boundariesCaseAndNoMutation(){var w=new ArrayList<>(List.of("a","A","abc","b","a"));assertEquals("[a-2, A-1, abc-1, b-1, a-2]",PublicChecks.report(TaskBinding.run("a1a_A abc2abcX ébЖB",w)));assertEquals(List.of("a","A","abc","b","a"),w);assertEquals("[x-0, x-0]",PublicChecks.report(TaskBinding.run("",List.of("x","x"))));assertEquals("[]",PublicChecks.report(TaskBinding.run("anything",List.of())));}
}
