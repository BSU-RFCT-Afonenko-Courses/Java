import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContractTest {
    private static String run(String input) throws Exception { return JavaProcess.runCheckingUncaught(Main.class, input).stdout(); }
    private static void exact(String expected, String actual) { assertTrue(actual.equals(expected) || actual.equals(expected + "\n") || actual.equals(expected + "\r\n"), "Сопоставьте текст результата с условием"); }
    @Test void month_1() throws Exception { exact("январь", run("1")); }
    @Test void month_2() throws Exception { exact("февраль", run("2")); }
    @Test void month_3() throws Exception { exact("март", run("3")); }
    @Test void month_4() throws Exception { exact("апрель", run("4")); }
    @Test void month_5() throws Exception { exact("май", run("5")); }
    @Test void month_6() throws Exception { exact("июнь", run("6")); }
    @Test void month_7() throws Exception { exact("июль", run("7")); }
    @Test void month_8() throws Exception { exact("август", run("8")); }
    @Test void month_9() throws Exception { exact("сентябрь", run("9")); }
    @Test void month_10() throws Exception { exact("октябрь", run("10")); }
    @Test void month_11() throws Exception { exact("ноябрь", run("11")); }
    @Test void month_12() throws Exception { exact("декабрь", run("12")); }
    @Test void zero() throws Exception { exact("Некорректный месяц", run("0")); }
    @Test void after_range() throws Exception { exact("Некорректный месяц", run("13")); }
    @Test void negative() throws Exception { exact("Некорректный месяц", run("-1")); }
    @Test void maximum_int() throws Exception { exact("Некорректный месяц", run("2147483647")); }
    @Test void minimum_int() throws Exception { exact("Некорректный месяц", run("-2147483648")); }
    @Test void fraction() throws Exception { exact("Некорректный месяц", run("1.5")); }
    @Test void word() throws Exception { exact("Некорректный месяц", run("abc")); }
    @Test void empty() throws Exception { exact("Некорректный месяц", run("")); }
    @Test void whitespace() throws Exception { exact("Некорректный месяц", run(" \t\n")); }
    @Test void first_token_only() throws Exception { exact("декабрь", run("12 abc 1")); }
    @Test void invalid_first_token() throws Exception { exact("Некорректный месяц", run("abc 1")); }
}
