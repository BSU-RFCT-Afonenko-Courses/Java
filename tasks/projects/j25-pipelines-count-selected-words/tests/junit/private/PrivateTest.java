import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void boundariesCaseAndNoMutation(){var w=new java.util.ArrayList<>(java.util.List.of("a","A","abc","b","a"));assertEquals("[a-2, A-1, abc-1, b-1, a-2]",TaskBinding.run("a1a_A abc2abcX ébЖB",w));assertEquals(java.util.List.of("a","A","abc","b","a"),w);assertEquals("[x-0, x-0]",TaskBinding.run("",java.util.List.of("x","x")));assertEquals("[]",TaskBinding.run("anything",java.util.List.of()));}
}
