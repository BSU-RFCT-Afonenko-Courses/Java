import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

public class ApiContract {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        Class<?> type = api.type("Study");
        api.expect(Modifier.isPublic(type.getModifiers()) && !type.isInterface() && !type.isEnum() && !type.isRecord(), "публичный класс Study", type.toGenericString());
        api.method("Study", "sort", int[].class, Modifier.PUBLIC | Modifier.STATIC, 0, int[].class);
        api.compiles("ComputeClient", "class ComputeClient { int[] call() throws Throwable { return Study.sort(new int[0]); } }", true);
    }
}
