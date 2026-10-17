import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void zeroEmptyCaseAndNewArray(){String[] a={"Ab","ac","Ab","aB",""};String[] before=a.clone();assertArrayEquals(new String[]{"ac","aB"},TaskBinding.run(a,'a',2));assertArrayEquals(before,a);assertArrayEquals(new String[0],TaskBinding.run(a,'A',0));assertArrayEquals(new String[0],TaskBinding.run(a,'Z',2));String[] e={};assertNotSame(e,TaskBinding.run(e,'a',1));String[] only={"aa"};String[] r=TaskBinding.run(only,'a',2);assertNotSame(only,r);r[0]="bb";assertEquals("aa",only[0]);}
}
