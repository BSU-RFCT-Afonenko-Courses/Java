import java.lang.Object;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
 static void select(Object[] input, Object[] expected, Supplier<Object[]> call) {
  Object[] refs = input.clone();
  List<?> before = PublicChecks.values(input);
  List<?> expectedValues = PublicChecks.values(expected);
  Object[] out = call.get();
  List<?> actual = PublicChecks.values(out);
  assertNotNull(out);
  for (int i = 0; i < input.length; i++) assertSame(refs[i], input[i]);
  assertEquals(before, PublicChecks.values(input));
  assertEquals(expectedValues, actual);
 }
 @Test void duplicatesEmptyNoMatchesAndProperties() {
  Object a = TaskBinding.customer(1,"A","B","C","0000000000000001"),
   b = TaskBinding.customer(2,"A","A","Z","0000000000000002"),
   c = TaskBinding.customer(3,"A","A","A","0000000000000003");
  Object[] in = TaskBinding.array(a,b,c,b);
  select(in, new Object[]{c,b,b,a}, () -> TaskBinding.sorted(in));
  select(in, new Object[]{b,b}, () -> TaskBinding.range(in,"0000000000000002","0000000000000002"));
  select(in, new Object[0], () -> TaskBinding.range(in,"0000000000000010","0000000000000020"));
  Object[] empty = TaskBinding.array();
  select(empty, new Object[0], () -> TaskBinding.sorted(empty));
  Object shortOne = TaskBinding.shortValue(9,"Short");
  assertFalse(TaskBinding.text(shortOne).isBlank());
  TaskBinding.edit(shortOne,7,"L","F","M","A","0000000000000007","0007");
  assertEquals(List.of(7L,"L","F","M","A","0000000000000007","0007"), PublicChecks.value(shortOne));
 }
}
