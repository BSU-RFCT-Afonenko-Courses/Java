import java.util.Map;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void independentAsciiBoundariesAndEmpty(){assertEquals(Map.of("alpha",2,"ALPHA",1,"a",2,"B",1),TaskBinding.words("alpha_alpha ALPHA a2aяB"));assertEquals(Map.of(),TaskBinding.words(""));assertEquals(Map.of("s",1,"t",1),TaskBinding.words("s't"));}
}
