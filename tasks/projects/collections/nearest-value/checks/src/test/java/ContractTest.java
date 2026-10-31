import java.lang.Double;
import java.lang.Object;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void duplicateSignedZerosAndRemoval(){Object s=TaskBinding.create();TaskBinding.add(s,-0.0);TaskBinding.add(s,0.0);TaskBinding.add(s,-0.0);assertEquals(0,Double.compare(-0.0,TaskBinding.nearest(s,0).orElseThrow()));TaskBinding.remove(s,-0.0);assertEquals(0,Double.compare(0.0,TaskBinding.nearest(s,0).orElseThrow()));TaskBinding.remove(s,0);assertTrue(TaskBinding.nearest(s,0).isEmpty());TaskBinding.add(s,-9);TaskBinding.add(s,1);TaskBinding.add(s,1);TaskBinding.remove(s,5);assertEquals(-9,TaskBinding.nearest(s,-4).orElseThrow());TaskBinding.remove(s,1);assertEquals(-9,TaskBinding.nearest(s,2).orElseThrow());}
}
