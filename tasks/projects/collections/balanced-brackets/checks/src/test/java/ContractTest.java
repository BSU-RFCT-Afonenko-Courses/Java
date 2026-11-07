import java.lang.Throwable;
import java.lang.String;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.*;

class ContractTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Соседние группы и несовпадающие виды скобок")
    void adjacentAndMismatchedKinds() throws Throwable {
        String s = "()[]{}([])";
        assertTrue(TaskBinding.valid(s));
        assertEquals("()[]{}([])", s);
        assertFalse(TaskBinding.valid("{]"));
        assertFalse(TaskBinding.valid("(()"));
        assertFalse(TaskBinding.valid("())"));
    }
}
