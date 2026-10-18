import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicThreeOperations(){var plus=TaskBinding.addition(7);assertEquals(5,plus.applyAsInt(-2));assertEquals(10,plus.applyAsInt(3));var matches=TaskBinding.matcher("[A-Z]+");assertTrue(matches.test("ABC"));assertFalse(matches.test("Ab"));var split=TaskBinding.splitter(",");assertArrayEquals(new String[]{"a","","b",""},split.apply("a,,b,"));assertArrayEquals(new String[]{""},split.apply(""));}
}
