import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicRadices(){assertEquals("1f",TaskBinding.run("31",16).toLowerCase(java.util.Locale.ROOT));assertEquals("11111",TaskBinding.run("31",2));assertEquals("0",TaskBinding.run("0",36));}
}
