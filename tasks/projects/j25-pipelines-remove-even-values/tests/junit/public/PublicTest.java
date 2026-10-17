import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicCompaction(){int[] a={2,3,4,3};TaskBinding.run(a);assertArrayEquals(new int[]{3,3,0,0},a);int[] b={-3,0,-2,-1};TaskBinding.run(b);assertArrayEquals(new int[]{-3,-1,0,0},b);}
}
