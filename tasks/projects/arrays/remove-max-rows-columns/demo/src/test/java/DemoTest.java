import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    @Test void remove_main() throws Throwable {
        MatrixResult result = Study.remove(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}});
        assertNotNull(result);
        assertEquals(1, result.rows());
        assertEquals(1, result.columns());
        assertNotNull(result.values());
        assertEquals(result.rows(), result.values().length);
        for (int[] row : result.values()) { assertNotNull(row); assertEquals(result.columns(), row.length); }
        assertArrayEquals(new int[][]{{2}}, result.values());
    }
}
