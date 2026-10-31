import java.lang.Integer;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void alternatingAllOddAndOne(){int[] a={0,9,-5,6,9,0,-7};TaskBinding.run(a);assertArrayEquals(new int[]{9,-5,9,-7,0,0,0},a);int[] odds={-7,1,Integer.MAX_VALUE};TaskBinding.run(odds);assertArrayEquals(new int[]{-7,1,Integer.MAX_VALUE},odds);int[] one={Integer.MIN_VALUE};TaskBinding.run(one);assertArrayEquals(new int[]{0},one);}
}
