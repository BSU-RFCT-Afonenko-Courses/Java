import java.lang.Throwable;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    @Test void rotate_90(TestReporter publicReporter) throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
    var publicActual = Study.rotate(source, 90);
    publicReporter.publishEntry(Map.of("public-case", "normal-90", "actual", Arrays.deepToString(publicActual) + "; source=" + Arrays.deepToString(source) + "; same=" + (source == publicActual)));

        int[][] result = publicActual;
        assertArrayEquals(new int[][]{{3,0,-2},{0,-1,3},{-3,2,1}}, result);
        assertNotSame(source, result);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, source);
    }
    @Test void rotate_180(TestReporter publicReporter) throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
    var publicActual = Study.rotate(source, 180);
    publicReporter.publishEntry(Map.of("public-case", "normal-180", "actual", Arrays.deepToString(publicActual) + "; source=" + Arrays.deepToString(source) + "; same=" + (source == publicActual)));

        int[][] result = publicActual;
        assertArrayEquals(new int[][]{{-2,3,1},{0,-1,2},{3,0,-3}}, result);
        assertNotSame(source, result);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, source);
    }
    @Test void rotate_270(TestReporter publicReporter) throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
    var publicActual = Study.rotate(source, 270);
    publicReporter.publishEntry(Map.of("public-case", "normal-270", "actual", Arrays.deepToString(publicActual) + "; source=" + Arrays.deepToString(source) + "; same=" + (source == publicActual)));

        int[][] result = publicActual;
        assertArrayEquals(new int[][]{{1,2,-3},{3,-1,0},{-2,0,3}}, result);
        assertNotSame(source, result);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, source);
    }
}
