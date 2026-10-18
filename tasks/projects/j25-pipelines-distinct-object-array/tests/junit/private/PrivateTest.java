import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateTest {
@Test void equalityOrderCaseAndNoMutation(){Object first=TaskBinding.make(2,"B"),second=TaskBinding.make(1,"A"),otherCase=TaskBinding.make(1,"a"),equal=TaskBinding.make(2,"B");assertEquals(first,equal);assertEquals(first.hashCode(),equal.hashCode());assertNotEquals(second,otherCase);Object[] a=TaskBinding.array(first,second,otherCase,equal);Object[] r=TaskBinding.run(a);assertEquals(3,r.length);assertSame(first,r[0]);assertSame(second,r[1]);assertSame(otherCase,r[2]);assertSame(first,a[0]);assertSame(equal,a[3]);assertEquals(2,TaskBinding.id(first));assertEquals("B",TaskBinding.name(first));Object[] e=TaskBinding.array();assertNotSame(e,TaskBinding.run(e));assertEquals(0,TaskBinding.run(e).length);Object[] one=TaskBinding.array(TaskBinding.make(Integer.MIN_VALUE,""));assertNotSame(one,TaskBinding.run(one));}
}
