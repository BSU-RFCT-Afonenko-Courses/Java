import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicNormalizedWord(){assertEquals(2,TaskBinding.run("Ёлка, елка; елками","елка"));}
}
