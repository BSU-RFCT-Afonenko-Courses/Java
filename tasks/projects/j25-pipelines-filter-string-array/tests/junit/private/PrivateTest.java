import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void unitsOrderAndNewArray(){String[] a={"😀","x","😀","", "abc"};String[] before=a.clone();assertArrayEquals(new String[]{"😀","😀","abc"},TaskBinding.run(a,1));assertArrayEquals(before,a);String[] e={};assertNotSame(e,TaskBinding.run(e,0));assertArrayEquals(new String[0],TaskBinding.run(new String[]{"a"},2));assertArrayEquals(new String[]{"abc"},TaskBinding.run(a,2));}
@Test void independentWhenEverythingMatches(){String[] a={"long","also"};String[] r=TaskBinding.run(a,0);assertNotSame(a,r);r[0]="changed";assertArrayEquals(new String[]{"long","also"},a);}
}
