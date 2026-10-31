import java.lang.Integer;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void independentSixValuesAndMultiplicity(){Integer[] a={7,7,-8,9,Integer.MIN_VALUE,Integer.MAX_VALUE};TaskBinding.run(a);assertArrayEquals(new Integer[]{Integer.MAX_VALUE,9,7,7,-8,Integer.MIN_VALUE},a);Integer[] same={4,4,4};TaskBinding.run(same);assertArrayEquals(new Integer[]{4,4,4},same);}
}
