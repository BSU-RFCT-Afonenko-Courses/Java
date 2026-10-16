import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void stableBoundariesAndInput(){int[][] a={{10000,10000},{3,4},{-3,-4},{0,0},{3,4},{-10000,10000}};int[][] before=java.util.Arrays.stream(a).map(int[]::clone).toArray(int[][]::new);assertEquals(java.util.List.of("(0:0)","(3:4)","(-3:-4)","(3:4)","(10000:10000)","(-10000:10000)"),PublicTest.tokens(TaskBinding.run(a)));assertTrue(java.util.Arrays.deepEquals(before,a));assertEquals(java.util.List.of(),PublicTest.tokens(TaskBinding.run(new int[0][])));}
}
