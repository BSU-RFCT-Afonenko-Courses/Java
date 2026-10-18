import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicDivisibility(){assertTrue(TaskBinding.run(0));assertTrue(TaskBinding.run(31));assertTrue(TaskBinding.run(-31));assertFalse(TaskBinding.run(32));}
}
