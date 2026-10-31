import java.lang.Object;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class HiddenContractTest {
 @Test void nameTieBreakAndRangeOrderFree() {
  Object z = TaskBinding.customer(91,"Я","Б","Г","9000000000000001"),
   a = TaskBinding.customer(92,"Я","А","Г","9000000000000002"),
   b = TaskBinding.customer(93,"Я","А","В","9000000000000003");
  Object[] in = TaskBinding.array(z,a,b,a), before = in.clone();
  List<?> state = PublicChecks.values(in), expectedSorted = PublicChecks.values(new Object[]{b,a,a,z});
  Object[] sorted = TaskBinding.sorted(in);
  List<?> actualSorted = PublicChecks.values(sorted);
  assertEquals(expectedSorted, actualSorted);
  for (int k = 0; k < in.length; k++) assertSame(before[k],in[k]);
  assertEquals(state, PublicChecks.values(in));
  Object[] beforeRange = in.clone();
  List<?> rangeState = PublicChecks.values(in);
  var expected = PublicChecks.bag(PublicChecks.values(new Object[]{a,b,a}));
  Object[] r = TaskBinding.range(in,"9000000000000002","9000000000000003");
  var got = PublicChecks.bag(PublicChecks.values(r));
  assertEquals(expected, got);
  for (int k = 0; k < in.length; k++) assertSame(beforeRange[k],in[k]);
  assertEquals(rangeState, PublicChecks.values(in));
 }
}
