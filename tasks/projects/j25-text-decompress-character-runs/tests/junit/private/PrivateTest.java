import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void lengthsCaseAndRoundTrip(){assertEquals("Aaabbbbbbbbbbbbb",TaskBinding.run("Aa2b13"));assertEquals("a".repeat(101)+"Z".repeat(12)+"x",TaskBinding.run("a101Z12x"));for(String text:new String[]{"abc","aaaBBc","z".repeat(37),"aabbCCd","AaA"})assertEquals(text,TaskBinding.run(compress(text)));}
static String compress(String s){var b=new StringBuilder();for(int i=0;i<s.length();){int j=i+1;while(j<s.length()&&s.charAt(j)==s.charAt(i))j++;b.append(s.charAt(i));if(j-i>1)b.append(j-i);i=j;}return b.toString();}
}
