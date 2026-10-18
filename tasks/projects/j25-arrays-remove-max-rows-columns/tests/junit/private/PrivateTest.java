import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void originalIndicesAndEmptyDimensions(){assertArrayEquals(new int[]{0,1},TaskBinding.run(new int[][]{{9,1},{9,2}}));assertArrayEquals(new int[]{1,0},TaskBinding.run(new int[][]{{9,9},{1,2}}));assertArrayEquals(new int[]{2,2,1,2,4,5},TaskBinding.run(new int[][]{{1,8,2,8},{3,9,3,3},{4,7,5,7},{6,6,6,9}}));assertArrayEquals(new int[]{0,0},TaskBinding.run(new int[][]{{7}}));}
}
