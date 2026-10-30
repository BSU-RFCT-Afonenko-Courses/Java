import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    @Test void sort_main() throws Throwable {
        assertArrayEquals(new int[]{-5,-2,0,3,7,7}, Study.sort(new int[]{7,-2,7,0,3,-5}));
    }
    @Test void sort_empty() throws Throwable {
        assertArrayEquals(new int[]{}, Study.sort(new int[]{}));
    }
    @Test void sort_single() throws Throwable {
        assertArrayEquals(new int[]{7}, Study.sort(new int[]{7}));
    }
}
