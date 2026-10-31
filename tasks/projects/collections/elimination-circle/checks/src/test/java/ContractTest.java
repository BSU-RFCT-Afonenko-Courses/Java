import java.util.List;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void independentSevenEight(){for(int k=0;k<2;k++){assertEquals(List.of(2,4,6,1,5,3),TaskBinding.removed(7,k));assertEquals(7,TaskBinding.survivor(7,k));assertEquals(List.of(2,4,6,8,3,7,5),TaskBinding.removed(8,k));assertEquals(1,TaskBinding.survivor(8,k));}}
}
