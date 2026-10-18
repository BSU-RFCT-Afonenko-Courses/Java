import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicMaximumPositions(){assertArrayEquals(new int[]{1,1,2},TaskBinding.run(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}));assertArrayEquals(new int[]{0,0},TaskBinding.run(new int[][]{{3,3},{3,3}}));}
}
