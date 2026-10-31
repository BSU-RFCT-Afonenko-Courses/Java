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
 @Test void emptyNoMatchesSameBoundaryAndProperties() {
  Object a = TaskBinding.patient(1,1,"Flu"), b = TaskBinding.patient(2,2,"flu");
  Object[] in = TaskBinding.array(a,b,a);
  select(in, new Object[]{b}, () -> TaskBinding.range(in,2,2));
  select(in, new Object[0], () -> TaskBinding.diagnosis(in,"FLU"));
  select(in, new Object[0], () -> TaskBinding.range(in,10,20));
  Object[] empty = TaskBinding.array();
  select(empty, new Object[0], () -> TaskBinding.diagnosis(empty,"x"));
  Object p = TaskBinding.shortValue(9,"Short");
  assertFalse(TaskBinding.text(p).isBlank());
  TaskBinding.edit(p,7,"L","F","M","A","007",7,"D");
  assertEquals(List.of(7L,"L","F","M","A","007",7L,"D"), PublicChecks.value(p));
 }
}
