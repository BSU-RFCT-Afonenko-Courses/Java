import java.lang.CharSequence;
import java.lang.Double;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void contravarianceCovarianceNullAndIdentity(){Object[] seen={null};Predicate<Object> c=x->{seen[0]=x;return x==null;};Function<Object,Integer> yes=x->null;Function<CharSequence,Double> no=x->(double)x.length();Function<String,Number> f=TaskBinding.run(c,yes,no);assertNull(f.apply(null));String v=new String("abc");assertEquals(3.0,f.apply(v));assertSame(v,seen[0]);}
@Test void exactExceptionAndStopping(){var e=new IllegalStateException("same");int[] calls={0};var c=TaskBinding.<Object,Object>run(x->{throw e;},x->{calls[0]++;return x;},x->{calls[0]++;return x;});assertSame(e,assertThrows(IllegalStateException.class,()->c.apply(new Object())));assertEquals(0,calls[0]);var b=TaskBinding.<Object,Object>run(x->false,x->{calls[0]++;return x;},x->{throw e;});assertSame(e,assertThrows(IllegalStateException.class,()->b.apply(null)));assertEquals(0,calls[0]);}
@Test void sameArgumentAndOrderOnBothBranches(){Object value=new Object();var trace=new ArrayList<String>();boolean[] branch={true};var f=TaskBinding.<Object,Object>run(x->{assertSame(value,x);trace.add("condition");return branch[0];},x->{assertSame(value,x);trace.add("true");return x;},x->{assertSame(value,x);trace.add("false");return null;});assertSame(value,f.apply(value));branch[0]=false;assertNull(f.apply(value));assertEquals(List.of("condition","true","condition","false"),trace);}
@Test void trueBranchPreservesException(){var e=new IllegalArgumentException("identity");var f=TaskBinding.<Object,Object>run(x->true,x->{throw e;},x->{fail("unselected");return x;});assertSame(e,assertThrows(IllegalArgumentException.class,()->f.apply(null)));}
@Test void laterApplyAfterCaughtExceptionIsIndependent(){var marker=new IllegalStateException("same current call");int[]calls={0,0,0};Object value=new Object();var f=TaskBinding.<Object,Object>run(x->{calls[0]++;if(calls[0]==1)throw marker;return calls[0]==3;},x->{calls[1]++;return x;},x->{calls[2]++;return x;});assertSame(marker,assertThrows(IllegalStateException.class,()->f.apply(value)));assertArrayEquals(new int[]{1,0,0},calls);assertSame(value,assertDoesNotThrow(()->f.apply(value)));assertArrayEquals(new int[]{2,0,1},calls);assertSame(value,assertDoesNotThrow(()->f.apply(value)));assertArrayEquals(new int[]{3,1,1},calls);}
}
