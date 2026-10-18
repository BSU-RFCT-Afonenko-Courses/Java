import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void bothGroupsAndDuplicates(){assertEquals("aeioubzz",TaskBinding.run("uoieazzb"));assertEquals("aeiou",TaskBinding.run("uoiea"));assertEquals("bbbyz",TaskBinding.run("zybbb"));assertEquals("aaa",TaskBinding.run("aaa"));}
}
