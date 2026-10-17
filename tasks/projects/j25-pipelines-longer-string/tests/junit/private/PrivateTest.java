import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void charUnitsAndFirstTie(){assertEquals("😀",TaskBinding.run("😀","x"));assertEquals("😀",TaskBinding.run("😀","ab"));assertEquals("long",TaskBinding.run("long",""));assertEquals("right",TaskBinding.run("","right"));}
}
