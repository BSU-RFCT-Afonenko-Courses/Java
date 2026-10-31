import java.lang.Object;
import java.util.List;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void ownValuesFirstReferencesAndNoChanges(){Object a=TaskBinding.make(17,"Q"),b=TaskBinding.make(17,"q"),c=TaskBinding.make(18,"Q"),d=TaskBinding.make(17,"Q");assertEquals(a,d);assertEquals(a.hashCode(),d.hashCode());assertNotEquals(a,b);assertNotEquals(a,c);Object[] input=TaskBinding.array(a,b,c,d,a);Object[] before=input.clone();var out=TaskBinding.run(input);assertNotSame(input,out);assertEquals(3,out.length);assertSame(a,out[0]);assertSame(b,out[1]);assertSame(c,out[2]);for(int i=0;i<input.length;i++)assertSame(before[i],input[i]);for(Object o:List.of(a,b,d))assertEquals(17,TaskBinding.id(o));assertEquals("Q",TaskBinding.name(a));assertEquals("q",TaskBinding.name(b));}
}
