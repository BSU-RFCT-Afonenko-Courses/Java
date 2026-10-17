import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicChoice(){assertEquals("XYZ",TaskBinding.run("ab","XYZ"));assertEquals("ab",TaskBinding.run("ab","cd"));assertEquals("",TaskBinding.run("",""));}
}
