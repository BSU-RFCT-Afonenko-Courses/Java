import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicSelection(){assertArrayEquals(new String[]{"Ab","Ab"},TaskBinding.run(new String[]{"Ab","ac","Ab","A",""},'A',2));}
}
