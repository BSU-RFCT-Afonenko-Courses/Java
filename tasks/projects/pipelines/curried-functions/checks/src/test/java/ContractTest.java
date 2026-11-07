import java.lang.String;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void independentCapturedSettingsAndWhitespaceTail(){var plus=TaskBinding.addition(6);var other=TaskBinding.addition(-9);assertEquals(8,plus.applyAsInt(2));assertEquals(-7,other.applyAsInt(2));assertEquals(5,plus.applyAsInt(-1));var digits=TaskBinding.matcher("[0-9]+");var letters=TaskBinding.matcher("[a-z]+");assertFalse(digits.test("a123b"));assertTrue(letters.test("abc"));assertTrue(digits.test("012"));var split=TaskBinding.splitter("\\s+");assertArrayEquals(new String[]{"a","b",""},split.apply("a b "));assertArrayEquals(new String[]{"","x",""},split.apply(" x "));}
}
