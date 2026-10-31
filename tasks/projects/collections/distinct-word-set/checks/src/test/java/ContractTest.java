import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.*;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;class ContractTest {
@Test void independentBoundariesAndLanguage(){Locale old=Locale.getDefault();try{for(Locale locale:List.of(Locale.ENGLISH,Locale.forLanguageTag("tr-TR"))){Locale.setDefault(locale);assertEquals(Set.of("alpha","a","b","i"),TaskBinding.words("ALPHA_alpha AяB2I"));assertEquals(Set.of(),TaskBinding.words(""));assertEquals(Set.of(),TaskBinding.words("ёж𐐀12"));}}finally{Locale.setDefault(old);}}
}
