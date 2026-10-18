import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void longRangeAndSigns(){assertArrayEquals(new long[]{3000000000L,3000000000L},TaskBinding.run("3000000000"));assertArrayEquals(new long[]{Long.MIN_VALUE,Long.MIN_VALUE},TaskBinding.run("-9223372036854775808"));assertArrayEquals(new long[]{0,0},TaskBinding.run("0","-1","1"));assertArrayEquals(new long[]{0,-1},TaskBinding.run("-1","1"));}
}
