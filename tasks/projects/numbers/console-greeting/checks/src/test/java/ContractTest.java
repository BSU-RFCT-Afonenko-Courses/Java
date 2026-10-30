import coursesupport.JavaProcess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ContractTest {
    private static String run(String input, String... args) throws Exception { return JavaProcess.runCheckingUncaught(Main.class,input,args).stdout(); }
    private static String[] tokens(String text) { return text.strip().split("\\p{javaWhitespace}+"); }
    private static void greeting(String expected, String... args) throws Exception {
        String value=run("stdin не имя",args).replace("\r\n","\n").replace("\r","\n");
        assertTrue(value.equals(expected)||value.equals(expected+"\n"),"Сохраните сообщение, имя и одну строку без лишнего вывода");
    }
    @Test void normal() throws Exception { greeting("Здравствуйте, Анна!","Анна"); }
    @Test void absent() throws Exception { greeting("Укажите имя."); }
    @Test void empty_present() throws Exception { greeting("Здравствуйте, !",""); }
    @Test void unicode_edges() throws Exception { greeting("Здравствуйте,  Ёж 🦔  !"," Ёж 🦔  "); }
    @Test void later_ignored() throws Exception { greeting("Здравствуйте, Майя!","Майя","ещё"); }
    @Test void combining() throws Exception { greeting("Здравствуйте, И\u0306ван!","И\u0306ван"); }
}
