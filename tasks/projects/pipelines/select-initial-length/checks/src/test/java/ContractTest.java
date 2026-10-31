import java.lang.Integer;
import java.lang.String;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void singletonIndependentAndFullN(){String[] input={"bc"};var out=TaskBinding.run(input,'b',2);assertNotSame(input,out);assertArrayEquals(new String[]{"bc"},out);out[0]="xx";assertEquals("bc",input[0]);String[] empty={};assertNotSame(empty,TaskBinding.run(empty,'A',Integer.MAX_VALUE));assertArrayEquals(new String[0],TaskBinding.run(new String[]{"a","ABC"},'A',Integer.MAX_VALUE));}
}
