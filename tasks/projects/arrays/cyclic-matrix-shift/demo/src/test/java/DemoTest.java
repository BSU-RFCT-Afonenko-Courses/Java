import java.lang.Throwable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    @Test void shift_1_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 1, 0);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
}
