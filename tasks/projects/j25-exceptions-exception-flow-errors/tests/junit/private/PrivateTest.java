import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void independentRuns(){assertEquals("B",TaskBinding.hierarchy(true));assertEquals("A",TaskBinding.hierarchy(false));assertEquals("10",TaskBinding.array(20));assertEquals("caught",TaskBinding.array(10));assertTrue(TaskBinding.checked());}
}
