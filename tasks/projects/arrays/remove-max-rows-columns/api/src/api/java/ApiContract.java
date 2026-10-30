import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

public class ApiContract {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        Class<?> type = api.type("Study");
        api.expect(Modifier.isPublic(type.getModifiers()) && !type.isInterface() && !type.isEnum() && !type.isRecord(), "публичный класс Study", type.toGenericString());
        api.method("Study", "remove", api.type("MatrixResult"), Modifier.PUBLIC | Modifier.STATIC, 0, int[][].class);
        Class<?> result = api.type("MatrixResult");
        api.expect(Modifier.isPublic(result.getModifiers()), "публичный MatrixResult", result.toGenericString());
        api.compiles("CarrierClient", "class CarrierClient { MatrixResult value() { return new MatrixResult(0, 1, new int[0][]); } }", true);
        api.method("MatrixResult", "rows", int.class, Modifier.PUBLIC, Modifier.STATIC);
        api.method("MatrixResult", "columns", int.class, Modifier.PUBLIC, Modifier.STATIC);
        api.method("MatrixResult", "values", int[][].class, Modifier.PUBLIC, Modifier.STATIC);
        api.compiles("ComputeClient", "class ComputeClient { MatrixResult call() throws Throwable { return Study.remove(new int[1][1]); } }", true);
    }
}
