import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    @Test void shift_1_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 1, 0);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
    @Test void shift_1_left() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 1, 1);
        assertArrayEquals(new int[][]{{0,3,-3},{-1,0,2},{3,-2,1}}, result);
    }
    @Test void shift_1_up() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 1, 2);
        assertArrayEquals(new int[][]{{2,-1,0},{1,3,-2},{-3,0,3}}, result);
    }
    @Test void shift_1_down() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 1, 3);
        assertArrayEquals(new int[][]{{1,3,-2},{-3,0,3},{2,-1,0}}, result);
    }
    @Test void shift_0_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 0, 0);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, result);
    }
    @Test void shift_3_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 3, 0);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, result);
    }
    @Test void shift_4_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 4, 0);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
    @Test void shift_2_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2, 0);
        assertArrayEquals(new int[][]{{0,3,-3},{-1,0,2},{3,-2,1}}, result);
    }
    @Test void shift_2_left() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2, 1);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
    @Test void shift_2_up() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2, 2);
        assertArrayEquals(new int[][]{{1,3,-2},{-3,0,3},{2,-1,0}}, result);
    }
    @Test void shift_2_down() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2, 3);
        assertArrayEquals(new int[][]{{2,-1,0},{1,3,-2},{-3,0,3}}, result);
    }
    @Test void shift_5_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 5, 0);
        assertArrayEquals(new int[][]{{0,3,-3},{-1,0,2},{3,-2,1}}, result);
    }
    @Test void shift_5_left() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 5, 1);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
    @Test void shift_5_up() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 5, 2);
        assertArrayEquals(new int[][]{{1,3,-2},{-3,0,3},{2,-1,0}}, result);
    }
    @Test void shift_5_down() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 5, 3);
        assertArrayEquals(new int[][]{{2,-1,0},{1,3,-2},{-3,0,3}}, result);
    }
    @Test void shift_6_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 6, 0);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, result);
    }
    @Test void shift_6_left() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 6, 1);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, result);
    }
    @Test void shift_6_up() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 6, 2);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, result);
    }
    @Test void shift_6_down() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 6, 3);
        assertArrayEquals(new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}}, result);
    }
    @Test void shift_7_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 7, 0);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
    @Test void shift_7_left() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 7, 1);
        assertArrayEquals(new int[][]{{0,3,-3},{-1,0,2},{3,-2,1}}, result);
    }
    @Test void shift_7_up() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 7, 2);
        assertArrayEquals(new int[][]{{2,-1,0},{1,3,-2},{-3,0,3}}, result);
    }
    @Test void shift_7_down() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 7, 3);
        assertArrayEquals(new int[][]{{1,3,-2},{-3,0,3},{2,-1,0}}, result);
    }
    @Test void shift_2147483647_right() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2147483647, 0);
        assertArrayEquals(new int[][]{{3,-3,0},{0,2,-1},{-2,1,3}}, result);
    }
    @Test void shift_2147483647_left() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2147483647, 1);
        assertArrayEquals(new int[][]{{0,3,-3},{-1,0,2},{3,-2,1}}, result);
    }
    @Test void shift_2147483647_up() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2147483647, 2);
        assertArrayEquals(new int[][]{{2,-1,0},{1,3,-2},{-3,0,3}}, result);
    }
    @Test void shift_2147483647_down() throws Throwable {
        int[][] source = new int[][]{{-3,0,3},{2,-1,0},{1,3,-2}};
        int[][] result = Study.shift(source, 2147483647, 3);
        assertArrayEquals(new int[][]{{1,3,-2},{-3,0,3},{2,-1,0}}, result);
    }
}
