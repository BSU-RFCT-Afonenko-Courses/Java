import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void transformed(String text, int count, String actual) {
        boolean matches = false;
        for (String first : new String[]{"\n", "\r\n"})
            for (String last : new String[]{"\n", "\r\n"})
                matches |= actual.equals(text + first + count + last);
        assertTrue(matches, "Сохраните весь исходный текст, счётчик и оба дополнительных перевода строки");
    }
    @Test void published() throws Exception { transformed("aБяZ 👋🙂", 2, run("AБяz 👋🙂.")); }
    @Test void empty() throws Exception { transformed("", 0, run("")); }
    @Test void immediate_point() throws Exception { transformed("", 0, run(".Az")); }
    @Test void ascii_edges() throws Exception { transformed("azAZ @[`{09 Бя🙂", 4, run("AZaz @[`{09 Бя🙂.extra AZ")); }
    @Test void multiline() throws Exception { transformed("aZ09\t_\n!?", 2, run("Az09\t_\n!?")); }
    @Test void newlines() throws Exception { transformed("a\r\nZ\r\n", 2, run("A\r\nz\r\n")); }
    @Test void unchanged_unicode() throws Exception { transformed("БЯяß😀", 0, run("БЯяß😀.ignored")); }
}
