import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicBranchesAndCreation(){int[] calls={0,0,0};var f=TaskBinding.<String,Integer>run(s->{calls[0]++;return s.isEmpty();},s->{calls[1]++;return 0;},s->{calls[2]++;return s.length();});assertArrayEquals(new int[]{0,0,0},calls);assertEquals(0,f.apply(""));assertEquals(3,f.apply("abc"));assertEquals(1,f.apply("x"));assertArrayEquals(new int[]{3,1,2},calls);}
@Test void unselectedBranchNotCalled(){var f=TaskBinding.<String,Integer>run(String::isEmpty,s->0,s->{throw new IllegalStateException();});assertEquals(0,f.apply(""));}
}
