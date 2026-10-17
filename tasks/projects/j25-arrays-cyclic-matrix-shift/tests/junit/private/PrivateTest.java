import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void normalizedShift(){for(int d=0;d<4;d++){assertTrue(java.util.Arrays.deepEquals(new int[][]{{7}},TaskBinding.run(new int[][]{{7}},Integer.MAX_VALUE,d)));int[][] a={{1,2},{3,4}};assertTrue(java.util.Arrays.deepEquals(a,TaskBinding.run(a,0,d)));assertTrue(java.util.Arrays.deepEquals(a,TaskBinding.run(a,4,d)));assertTrue(java.util.Arrays.deepEquals(TaskBinding.run(new int[][]{{1,2},{3,4}},1,d),TaskBinding.run(new int[][]{{1,2},{3,4}},Integer.MAX_VALUE,d)));}}
}
