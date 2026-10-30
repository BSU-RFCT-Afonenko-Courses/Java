import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
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
    @Test void remove_0x0() throws Throwable {
        MatrixResult result = Study.remove(new int[][]{{3,3},{3,3}});
        assertNotNull(result);
        assertEquals(0, result.rows());
        assertEquals(0, result.columns());
        assertNotNull(result.values());
        assertEquals(result.rows(), result.values().length);
        for (int[] row : result.values()) { assertNotNull(row); assertEquals(result.columns(), row.length); }
        assertArrayEquals(new int[][]{}, result.values());
    }
    @Test void remove_0x1() throws Throwable {
        MatrixResult result = Study.remove(new int[][]{{9,1},{9,2}});
        assertNotNull(result);
        assertEquals(0, result.rows());
        assertEquals(1, result.columns());
        assertNotNull(result.values());
        assertEquals(result.rows(), result.values().length);
        for (int[] row : result.values()) { assertNotNull(row); assertEquals(result.columns(), row.length); }
        assertArrayEquals(new int[][]{}, result.values());
    }
    @Test void remove_1x0() throws Throwable {
        MatrixResult result = Study.remove(new int[][]{{9,9},{1,2}});
        assertNotNull(result);
        assertEquals(1, result.rows());
        assertEquals(0, result.columns());
        assertNotNull(result.values());
        assertEquals(result.rows(), result.values().length);
        for (int[] row : result.values()) { assertNotNull(row); assertEquals(result.columns(), row.length); }
        assertArrayEquals(new int[][]{{}}, result.values());
    }
}
