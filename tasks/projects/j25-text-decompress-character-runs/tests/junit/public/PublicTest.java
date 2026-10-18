import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicRuns(){assertEquals("aaaaaaaaaaaab",TaskBinding.run("a12b"));assertEquals("hellowoooorld",TaskBinding.run("hel2owo4rld"));assertEquals("",TaskBinding.run(""));}
}
