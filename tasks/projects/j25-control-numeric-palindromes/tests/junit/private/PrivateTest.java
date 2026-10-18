import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void minimumDuplicatesAndNoMatch(){assertEquals(java.util.List.of("-2147447412","11","11","-11"),PublicTest.tokens(TaskBinding.run(new int[]{Integer.MIN_VALUE,-2147447412,11,10,11,-11})));assertEquals("Подходящих чисел нет",TaskBinding.run(new int[]{10,-120,123}).trim());}
}
