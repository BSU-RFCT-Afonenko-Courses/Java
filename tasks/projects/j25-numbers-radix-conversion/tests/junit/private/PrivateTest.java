import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void representationAndNoArtificialLongLimit(){for(int b:new int[]{2,3,7,10,16,36})for(String s:new String[]{"0","1","35","36","9223372036854775808","123456789012345678901234567890"}){String v=TaskBinding.run(s,b);assertTrue(v.matches("[0-9a-z]+")||v.matches("[0-9A-Z]+"));assertFalse(v.length()>1&&v.startsWith("0"));assertEquals(new java.math.BigInteger(s),new java.math.BigInteger(v,b));}}
}
