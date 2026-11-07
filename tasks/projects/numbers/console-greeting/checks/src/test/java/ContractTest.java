import static org.junit.jupiter.api.Assertions.*;

import coursesupport.JavaProcess;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

class ContractTest {
    private static String run(TestReporter publicReporter, String[] publicCaseIds, String input,
            String... args) throws Exception {
        return publicOutput(publicReporter, publicCaseIds,
                JavaProcess.runCheckingUncaught(Main.class, input, args).stdout());
    }

    private static String[] tokens(String text) {
        return text.strip().split("\\p{javaWhitespace}+");
    }

    private static void greeting(TestReporter publicReporter, String[] publicCaseIds,
            String expected, String... args) throws Exception {
        String value = run(publicReporter, publicCaseIds, "stdin не имя", args)
                               .replace("\r\n", "\n")
                               .replace("\r", "\n");
        assertEquals(expected,
                value.endsWith("\n") ? value.substring(0, value.length() - 1) : value,
                "Сохраните сообщение, имя и одну строку без лишнего вывода");
    }

    @Test
    @DisplayName("Первый аргумент Анна: приветствие с неизменённым именем")
    void normal(TestReporter publicReporter) throws Exception {
        String publicActual = publicOutput(publicReporter, new String[] {"normal"},
                JavaProcess.runCheckingUncaught(Main.class, "", "Анна").stdout())
                                      .replace("\r\n", "\n")
                                      .replace("\r", "\n");
        assertEquals("Здравствуйте, Анна!",
                publicActual.endsWith("\n") ? publicActual.substring(0, publicActual.length() - 1)
                                            : publicActual,
                "Сохраните прежнее открытое сообщение");
        greeting(publicReporter, new String[] {"demo-normal"}, "Здравствуйте, Анна!", "Анна");
    }

    @Test
    @DisplayName("Без аргументов: сообщение «Укажите имя.»")
    void absent(TestReporter publicReporter) throws Exception {
        String publicActual = publicOutput(publicReporter, new String[] {"boundary"},
                JavaProcess.runCheckingUncaught(Main.class, "").stdout())
                                      .replace("\r\n", "\n")
                                      .replace("\r", "\n");
        assertEquals("Укажите имя.",
                publicActual.endsWith("\n") ? publicActual.substring(0, publicActual.length() - 1)
                                            : publicActual,
                "Сохраните прежнее открытое сообщение");
        greeting(publicReporter, new String[] {"demo-absent"}, "Укажите имя.");
    }

    @Test
    @DisplayName("Пустой первый аргумент отличается от отсутствующего")
    void empty_present() throws Exception {
        greeting(null, null, "Здравствуйте, !", "");
    }

    @Test
    @DisplayName("Кириллица, эмодзи и пробелы по краям имени сохраняются")
    void unicode_edges() throws Exception {
        greeting(null, null, "Здравствуйте,  Ёж 🦔  !", " Ёж 🦔  ");
    }

    @Test
    @DisplayName("Последующие аргументы не меняют имя")
    void later_ignored() throws Exception {
        greeting(null, null, "Здравствуйте, Майя!", "Майя", "ещё");
    }

    @Test
    @DisplayName("Составной Unicode-символ сохраняется без нормализации")
    void combining() throws Exception {
        greeting(null, null, "Здравствуйте, И\u0306ван!", "И\u0306ван");
    }

    /** Только прежние PUBLIC вызовы передают reporter; private-вызовы передают null. */
    private static String publicOutput(TestReporter reporter, String[] ids, String actual) {
        if (reporter != null) {
            String visible = actual.replaceAll("[\\p{Cntrl}&&[^\n\t]]", "�");
            if (visible.length() > 2000)
                visible = visible.substring(0, 2000) + "…";
            for (String id : ids)
                reporter.publishEntry(Map.of("public-case", id, "actual", "stdout=" + visible));
        }
        return actual;
    }
}
