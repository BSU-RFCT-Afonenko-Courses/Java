import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

/** Допуск обязательной операции без инициализации студенческого класса. */
public class ApiContract {
  public static void check(PlatformApiPrecheck.Api api) throws Exception {
    Class<?> study = api.type("Study");
    api.expect(
        Modifier.isPublic(study.getModifiers()), "public class Study без пакета", study.toString());
    var method = api.method(
        "Study", "accepts", boolean.class, Modifier.PUBLIC | Modifier.STATIC, 0, String.class);
  }
}
