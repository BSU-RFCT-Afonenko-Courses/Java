import java.lang.Object;
import java.util.Set;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void differentEdgesAndIsolatedVertexPersistence(){Object g=TaskBinding.graph(5);TaskBinding.add(g,0,3);TaskBinding.add(g,3,4);assertEquals(Set.of(0,4),TaskBinding.neighbors(g,3));assertTrue(TaskBinding.edge(g,4,3));TaskBinding.remove(g,0,3);assertEquals(Set.of(),TaskBinding.neighbors(g,0));assertEquals(Set.of(4),TaskBinding.neighbors(g,3));assertEquals(Set.of(),TaskBinding.neighbors(g,1));TaskBinding.add(g,0,2);assertTrue(TaskBinding.edge(g,2,0));assertEquals(Set.of(2),TaskBinding.neighbors(g,0));}
}
