import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void boundariesAndBothPairs(){assertEquals(3,TaskBinding.run("май МАЙ, маи; маий","МАИ"));assertEquals(3,TaskBinding.run("x1X_xx\tx!","X"));assertEquals(0,TaskBinding.run("123 !\n","я"));assertEquals(0,TaskBinding.run("","е"));assertEquals(3,TaskBinding.run("е Ё ёж е","ё"));}
}
