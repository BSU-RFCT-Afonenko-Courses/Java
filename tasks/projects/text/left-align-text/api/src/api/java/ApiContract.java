import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

/** Допуск обязательной операции без инициализации студенческого класса. */
public class ApiContract {
  public static void check(PlatformApiPrecheck.Api api) throws Exception {
    Class<?> study = api.type("Study");
    api.expect(
        Modifier.isPublic(study.getModifiers()), "public class Study без пакета", study.toString());
    var method = api.method("Study", "align", List.class,
        Modifier.PUBLIC | Modifier.STATIC, 0, String.class, int.class);
    api.expect(method.getGenericReturnType().equals(new ParameterizedType() {
      public Type[] getActualTypeArguments() {
        return new Type[] {String.class};
      }
      public Type getRawType() {
        return List.class;
      }
      public Type getOwnerType() {
        return null;
      }
    }),
        "java.util.List<String> Study.align(String, int)",
        method.getGenericReturnType().getTypeName());
  }
}
