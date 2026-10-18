import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
static int[] numbers(String s){return s.isBlank()?new int[0]:java.util.Arrays.stream(s.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();}
@Test void publicPrimes(){assertArrayEquals(new int[]{2,3,5,7,11},numbers(TaskBinding.run(5)));assertArrayEquals(new int[0],numbers(TaskBinding.run(0)));}
}
