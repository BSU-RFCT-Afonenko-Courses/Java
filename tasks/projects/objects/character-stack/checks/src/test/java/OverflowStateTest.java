import java.lang.Object;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class OverflowStateTest {
 @Test void overflowKeepsTwoAndSeparateInstances() {
  Object first=TaskBinding.create(2),second=TaskBinding.create(1);
  assertEquals("Стек переполнен\n",ContractTest.capture(()->{TaskBinding.push(first,'A');TaskBinding.push(first,'B');TaskBinding.push(first,'C');}));
  assertEquals("",ContractTest.capture(()->{TaskBinding.push(second,'Ж');assertEquals('B',TaskBinding.pop(first));assertEquals('Ж',TaskBinding.pop(second));assertEquals('A',TaskBinding.pop(first));}));
 }
}
