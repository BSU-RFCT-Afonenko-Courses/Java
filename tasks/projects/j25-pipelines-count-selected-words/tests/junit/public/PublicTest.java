import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PublicTest {
@Test void publicQueries(){assertEquals("[one-1, two-2, absent-0, one-1]",TaskBinding.run("one, two two; One",java.util.List.of("one","two","absent","one")));}
}
