import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicDiscriminants(){assertEquals(1,TaskBinding.run(1,-3,2),1e-8);assertEquals(0,TaskBinding.run(1,2,1),1e-8);assertEquals(-4,TaskBinding.run(1,0,1),1e-8);}
}
