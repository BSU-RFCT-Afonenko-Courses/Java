import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicFrequency(){assertEquals(java.util.Map.of(97,2L,98,1L,32,1L),TaskBinding.run("ab a"));assertEquals(java.util.Map.of(55357,1L,56832,1L),TaskBinding.run("😀"));}
}
