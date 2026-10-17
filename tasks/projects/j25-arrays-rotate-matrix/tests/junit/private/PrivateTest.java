import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void originalAndNewArray(){int[][] a={{1,2},{3,4}};int[][] copy={{1,2},{3,4}};for(int angle:new int[]{90,180,270}){int[][] result=TaskBinding.run(a,angle);assertNotSame(a,result);assertTrue(java.util.Arrays.deepEquals(copy,a));}assertTrue(java.util.Arrays.deepEquals(new int[][]{{7}},TaskBinding.run(new int[][]{{7}},90)));}
}
