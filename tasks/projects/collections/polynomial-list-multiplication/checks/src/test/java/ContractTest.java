import java.lang.Double;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void independentZeroLengthCancellationTolerance(){var a=new ArrayList<>(List.of(2.0,-2.0,0.0));var b=new ArrayList<>(List.of(0.5,0.5));var r=TaskBinding.multiply(a,b);assertEquals(4,r.size());double[] expected={1,0,-1,0};for(int i=0;i<4;i++)assertTrue(Double.isFinite(r.get(i))&&Math.abs(r.get(i)-expected[i])<=1e-6+1e-12*Math.abs(expected[i]));assertEquals(List.of(2.0,-2.0,0.0),a);assertEquals(List.of(0.5,0.5),b);assertNotSame(a,r);assertNotSame(b,r);a.set(0,7.0);b.set(0,9.0);assertTrue(Math.abs(r.get(0)-1)<=1e-6+1e-12);assertTrue(TaskBinding.multiply(List.of(),List.of()).isEmpty());}
}
