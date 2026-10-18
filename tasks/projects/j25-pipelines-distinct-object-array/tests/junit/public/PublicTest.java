import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicFirstIdentity(){Object a=TaskBinding.make(1,"A"),b=TaskBinding.make(2,"B"),same=TaskBinding.make(1,"A");Object[] input=TaskBinding.array(a,b,same);Object[] result=TaskBinding.run(input);assertEquals(2,result.length);assertSame(a,result[0]);assertSame(b,result[1]);assertArrayEquals(TaskBinding.array(a,b,same),input);}
}
