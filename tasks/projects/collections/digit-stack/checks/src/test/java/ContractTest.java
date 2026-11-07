import java.lang.Throwable;
import java.lang.Long;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Повторяющиеся нули и границы long")
    void repeatedZerosAndLongBoundary() throws Throwable {
        assertEquals("0001", TaskBinding.digits(1000));
        assertEquals("0101", TaskBinding.digits(1010));
        assertEquals("7085774586302733229", TaskBinding.digits(Long.MAX_VALUE));
        assertEquals("7", TaskBinding.digits(7));
    }
}
