import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
static java.util.List<String> tokens(String s){return java.util.List.of(s.trim().split("\\s+"));}
@Test void publicPalindromes(){assertEquals(java.util.List.of("-121","121","7","0"),tokens(TaskBinding.run(new int[]{-121,121,7,0,10})));assertEquals("Подходящих чисел нет",TaskBinding.run(new int[0]).trim());}
}
