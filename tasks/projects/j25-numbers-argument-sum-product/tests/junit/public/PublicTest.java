import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicArguments(){assertArrayEquals(new long[]{0,1},TaskBinding.run());assertArrayEquals(new long[]{5,-24},TaskBinding.run("3","-2","4"));}
}
