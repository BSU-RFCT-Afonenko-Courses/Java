import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(String... args) throws Exception {
        return JavaProcess.runCheckingUncaught(Main.class,"",args).stdout();
    }
    private static String[] tokens(String text) {
        return text.isBlank()?new String[0]:text.strip().split("\\p{javaWhitespace}+");
    }
    private static void representation(String expected,String... args) throws Exception {
        String output=run(args).strip();
        assertTrue(output.equals(expected)||output.equals(expected.toUpperCase(java.util.Locale.ROOT)),"Та же величина, единый регистр и отсутствие ведущих нулей: "+expected);
    }
    @Test @DisplayName("Буквенная цифра одного регистра. Вход: 31 16")
    void hex_letter() throws Exception { representation("1f","31","16"); }
    @Test @DisplayName("Нулевое значение при верхней границе основания. Вход: 0 36")
    void zero_base36() throws Exception { representation("0","0","36"); }
    @Test @DisplayName("Нижняя граница основания. Вход: 31 2")
    void base2() throws Exception { representation("11111","31","2"); }
    @Test @DisplayName("Старшая буквенная цифра. Вход: 35 36")
    void digit_z() throws Exception { representation("z","35","36"); }
    @Test @DisplayName("Промежуточное основание. Вход: 36 3")
    void base3() throws Exception { representation("1100","36","3"); }
    @Test @DisplayName("Значение выше Long.MAX_VALUE. Вход: 9223372036854775808 16")
    void above_long() throws Exception { representation("8000000000000000","9223372036854775808","16"); }
    @Test @DisplayName("Число из 60 десятичных цифр без сужения диапазона. Вход: 123456789012345678901234567890123456789012345678901234567890 7")
    void large_base7() throws Exception { representation("6010406501403050001163255632406216450612530354214262643555010363621230","123456789012345678901234567890123456789012345678901234567890","7"); }
    @Test @DisplayName("Нуль при нижней границе основания. Вход: 0 2")
    void zero_base2() throws Exception { representation("0","0","2"); }
    @Test @DisplayName("Единый регистр нескольких буквенных цифр (AB или ab). Вход: 371 36")
    void two_letters() throws Exception { representation("ab","371","36"); }
    @Test @DisplayName("Ведущие нули во входе не переносятся в ответ. Вход: 00031 16")
    void leading_input_zero() throws Exception { representation("1f","00031","16"); }
}
