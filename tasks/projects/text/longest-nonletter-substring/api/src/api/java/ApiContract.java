import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

/** Допуск обязательной операции без инициализации студенческого класса. */
public class ApiContract {
  public static void check(PlatformApiPrecheck.Api api) throws Exception {
    Class<?> study = api.type("Study");
    api.expect(
        Modifier.isPublic(study.getModifiers()), "public class Study без пакета", study.toString());
    var method = api.method(
        "Study", "longest", String.class, Modifier.PUBLIC | Modifier.STATIC, 0, String.class);
  }
}
