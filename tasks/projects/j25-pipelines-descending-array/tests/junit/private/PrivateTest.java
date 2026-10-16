import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void overflowSafeComparatorAndEmpty(){Integer[] a={Integer.MIN_VALUE,Integer.MAX_VALUE,0,-1,Integer.MAX_VALUE};TaskBinding.run(a);assertArrayEquals(new Integer[]{Integer.MAX_VALUE,Integer.MAX_VALUE,0,-1,Integer.MIN_VALUE},a);Integer[] empty={};TaskBinding.run(empty);assertArrayEquals(new Integer[0],empty);}
}
