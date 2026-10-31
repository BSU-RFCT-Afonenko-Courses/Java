import java.lang.String;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void valueNotIdentityAndUtf16(){String first=new String("bb"),second=new String("cc");assertEquals(first,TaskBinding.run(first,second));assertEquals("😀",TaskBinding.run("x","😀"));assertEquals("",TaskBinding.run("",""));assertEquals("longer",TaskBinding.run("longer",""));}
}
