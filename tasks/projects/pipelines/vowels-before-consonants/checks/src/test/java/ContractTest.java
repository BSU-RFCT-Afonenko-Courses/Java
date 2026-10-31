import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;import java.util.*;import java.math.*;class ContractTest {
@Test void repeatedOAndEmpty(){assertEquals("oooot",TaskBinding.run("toooo"));assertEquals("",TaskBinding.run(""));assertEquals("aeeiouz",TaskBinding.run("zoueiea"));}
}
