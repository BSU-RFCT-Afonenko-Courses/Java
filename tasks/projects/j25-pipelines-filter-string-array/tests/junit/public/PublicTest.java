import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicThresholds(){assertArrayEquals(new String[]{"ccc"},TaskBinding.run(new String[]{"a","bb","bb","ccc"},2));assertArrayEquals(new String[]{"bb","bb","ccc"},TaskBinding.run(new String[]{"a","bb","bb","ccc"},1));assertArrayEquals(new String[]{"x"},TaskBinding.run(new String[]{"","x"},0));}
}
