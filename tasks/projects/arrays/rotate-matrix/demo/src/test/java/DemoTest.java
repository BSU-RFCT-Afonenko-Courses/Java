import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    @Test void rotate_90() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.rotate(source, 90);
        assertArrayEquals(new int[][]{{3,0,-2},{0,-1,3},{-3,2,1}}, result);
        assertNotSame(source, result);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, source);
    }
}
