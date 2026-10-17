import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void extremesEmptyAndAllEven(){int[] a={Integer.MIN_VALUE,Integer.MAX_VALUE,-1,2,Integer.MAX_VALUE};TaskBinding.run(a);assertArrayEquals(new int[]{Integer.MAX_VALUE,-1,Integer.MAX_VALUE,0,0},a);int[] e={};TaskBinding.run(e);assertArrayEquals(new int[0],e);int[] even={0,-2,4};TaskBinding.run(even);assertArrayEquals(new int[3],even);}
}
