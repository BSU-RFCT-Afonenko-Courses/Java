import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void independentFirstSixAndFortyTwo(){assertArrayEquals(new int[]{2,3,5,7,11,13},PublicChecks.numbers(TaskBinding.run(6)));int[] a=PublicChecks.numbers(TaskBinding.run(42));assertEquals(42,a.length);assertEquals(181,a[41]);for(int i=0;i<a.length;i++){assertTrue(a[i]>=2);for(int d=2;d<=a[i]/d;d++)assertNotEquals(0,a[i]%d);if(i>0)assertTrue(a[i]>a[i-1]);}}
}
