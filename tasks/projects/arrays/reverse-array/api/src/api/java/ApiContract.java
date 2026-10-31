import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

public class ApiContract {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        Class<?> type = api.type("Main");
        api.expect(Modifier.isPublic(type.getModifiers()), "публичный класс Main", type.toGenericString());
        api.method("Main", "main", void.class, Modifier.PUBLIC | Modifier.STATIC, 0, String[].class);
    }
}
