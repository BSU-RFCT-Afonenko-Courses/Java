import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void arrayFailureAndSuccess(){assertEquals("caught",TaskBinding.array(10));assertEquals("10",TaskBinding.array(20));}
@Test void specificAndGeneralHandlers(){assertEquals("A",TaskBinding.hierarchy(false));assertEquals("B",TaskBinding.hierarchy(true));}
@Test void checkedException(){assertTrue(Exception.class.isAssignableFrom(TaskBinding.exceptionType()));assertFalse(RuntimeException.class.isAssignableFrom(TaskBinding.exceptionType()));assertTrue(TaskBinding.checked());}
}
