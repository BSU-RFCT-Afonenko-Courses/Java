import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void boundsWholeMatchAndRepeatedFunctions(){var low=TaskBinding.addition(-1000000);assertEquals(-2000000,low.applyAsInt(-1000000));assertEquals(0,low.applyAsInt(1000000));assertEquals(2000000,TaskBinding.addition(1000000).applyAsInt(1000000));var matches=TaskBinding.matcher("[A-Z]+");assertFalse(matches.test("xABCy"));assertFalse(matches.test(""));assertTrue(TaskBinding.matcher(".*").test(""));var split=TaskBinding.splitter("[0-9]+");assertArrayEquals(new String[]{"","a","b",""},split.apply("1a22b3"));assertArrayEquals(new String[]{"abc"},split.apply("abc"));}
}
