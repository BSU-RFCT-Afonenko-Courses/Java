import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
static java.math.BigDecimal d(String s){return new java.math.BigDecimal(s);} static void values(java.util.List<java.math.BigDecimal> a,String... expected){assertEquals(expected.length,a.size());for(int i=0;i<expected.length;i++)assertEquals(0,a.get(i).compareTo(d(expected[i])),"price "+i);}
@Test void publicPrices(){values(TaskBinding.run(java.util.List.of(d("100"),d("0")),d("0.20")),"120.00","0.00");assertTrue(TaskBinding.run(java.util.List.of(),d("1")).isEmpty());}
}
