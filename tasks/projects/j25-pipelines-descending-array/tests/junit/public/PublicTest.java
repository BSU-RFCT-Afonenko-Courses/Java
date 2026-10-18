import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicDescending(){Integer[] a={2,-1,2,0};TaskBinding.run(a);assertArrayEquals(new Integer[]{2,2,0,-1},a);}
}
