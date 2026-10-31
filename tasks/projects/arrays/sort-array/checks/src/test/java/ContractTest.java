import java.lang.Object;
import java.lang.Throwable;
import org.junit.jupiter.api.TestReporter;
import java.util.Map;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    @Test void sort_main(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.sort(new int[]{7,-2,7,0,3,-5});
    publicReporter.publishEntry(Map.of("public-case", "normal", "actual", Arrays.deepToString(new Object[]{publicActual})));

        assertArrayEquals(new int[]{-5,-2,0,3,7,7}, publicActual);
    }
    @Test void sort_empty(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.sort(new int[]{});
    publicReporter.publishEntry(Map.of("public-case", "boundary-empty", "actual", Arrays.deepToString(new Object[]{publicActual})));

        assertArrayEquals(new int[]{}, publicActual);
    }
    @Test void sort_single(TestReporter publicReporter) throws Throwable {
    var publicActual = Study.sort(new int[]{7});
    publicReporter.publishEntry(Map.of("public-case", "boundary-single", "actual", Arrays.deepToString(new Object[]{publicActual})));

        assertArrayEquals(new int[]{7}, publicActual);
    }
}
