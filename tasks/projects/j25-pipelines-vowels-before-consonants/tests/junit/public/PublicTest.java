import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicGroups(){assertEquals("aajv",TaskBinding.run("java"));assertEquals("",TaskBinding.run(""));}
}
