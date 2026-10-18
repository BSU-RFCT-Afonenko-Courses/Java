import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
static int[][] copy(int[][] a){return java.util.Arrays.stream(a).map(int[]::clone).toArray(int[][]::new);}
@Test void publicFourDirections(){int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}};assertTrue(java.util.Arrays.deepEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}},TaskBinding.run(copy(a),1,0)));assertTrue(java.util.Arrays.deepEquals(new int[][]{{0,3,-3},{-1,0,2},{3,-2,1}},TaskBinding.run(copy(a),1,1)));assertTrue(java.util.Arrays.deepEquals(new int[][]{{2,-1,0},{1,3,-2},{-3,0,3}},TaskBinding.run(copy(a),1,2)));assertTrue(java.util.Arrays.deepEquals(new int[][]{{1,3,-2},{-3,0,3},{2,-1,0}},TaskBinding.run(copy(a),1,3)));}
}
