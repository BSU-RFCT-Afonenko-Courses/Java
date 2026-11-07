import java.lang.Throwable;
import java.util.Locale;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

class HistoricalContractTest {

    @Test
    @org.junit.jupiter.api.DisplayName("Английские слова независимо от локали машины")
    void englishBoundariesAndLocaleIndependence() throws Throwable {
        Locale old = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertEquals(Set.of("i", "java", "code"), TaskBinding.words("I i!JAVA/code_Java"));
            assertEquals(Set.of("a", "b"), TaskBinding.words("AяB"));
        } finally {
            Locale.setDefault(old);
        }
    }
}
