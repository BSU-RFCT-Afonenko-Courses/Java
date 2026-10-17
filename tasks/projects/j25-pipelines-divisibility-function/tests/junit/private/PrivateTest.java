import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void extremesAndMultiples(){for(int n:new int[]{Integer.MIN_VALUE,Integer.MAX_VALUE,-62,62,-32,30,961,-961})assertEquals(n%31==0,TaskBinding.run(n),"n="+n);}
}
