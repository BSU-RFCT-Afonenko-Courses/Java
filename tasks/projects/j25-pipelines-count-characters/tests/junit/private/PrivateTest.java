import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void allCharUnits(){assertEquals(java.util.Map.of(),TaskBinding.run(""));assertEquals(java.util.Map.of(0,2L,10,1L,32,2L,55357,2L,56832,1L),TaskBinding.run("\0 \n😀\uD83D \0"));}
}
