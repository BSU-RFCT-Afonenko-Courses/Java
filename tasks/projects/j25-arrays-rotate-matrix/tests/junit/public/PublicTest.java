import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicAngles(){int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}};assertTrue(java.util.Arrays.deepEquals(new int[][]{{3,0,-2},{0,-1,3},{-3,2,1}},TaskBinding.run(a,90)));assertTrue(java.util.Arrays.deepEquals(new int[][]{{-2,3,1},{0,-1,2},{3,0,-3}},TaskBinding.run(a,180)));assertTrue(java.util.Arrays.deepEquals(new int[][]{{1,2,-3},{3,-1,0},{-2,0,3}},TaskBinding.run(a,270)));}
}
