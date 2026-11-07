import java.lang.Throwable;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Повторы, длина UTF-16 и независимость результата")
    void repeatsUtf16AndIndependentResult() throws Throwable {
        var input = new ArrayList<>(List.of("bb", "😀", "a", "bb", "", "cd"));
        var before = new ArrayList<>(input);
        var out = TaskBinding.ordered(input);
        assertEquals(List.of("", "a", "bb", "😀", "bb", "cd"), out);
        assertEquals(before, input);
        assertNotSame(input, out);
        input.set(0, "changed");
        assertEquals(List.of("", "a", "bb", "😀", "bb", "cd"), out);
    }
}
