import java.lang.Object;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class CharDomainTest {@Test void nonAsciiAndReservedReturn(){for(int kind=0;kind<3;kind++){Object q=TaskBinding.queue(kind,1);assertEquals("",ContractTest.capture(()->{TaskBinding.put(q,'\uffff');assertEquals('\uffff',TaskBinding.get(q));}));assertEquals("Очередь пуста\n",ContractTest.capture(()->assertEquals('\0',TaskBinding.get(q))));}}}
