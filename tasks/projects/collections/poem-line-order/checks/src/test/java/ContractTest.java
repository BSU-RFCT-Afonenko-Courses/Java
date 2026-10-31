import java.util.ArrayList;
import java.util.List;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void repeatsUtf16AndIndependentResult(){var input=new ArrayList<>(List.of("bb","😀","a","bb","","cd"));var before=new ArrayList<>(input);var out=TaskBinding.ordered(input);assertEquals(List.of("","a","bb","😀","bb","cd"),out);assertEquals(before,input);assertNotSame(input,out);input.set(0,"changed");assertEquals(List.of("","a","bb","😀","bb","cd"),out);}
}
