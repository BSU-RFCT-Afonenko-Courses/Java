import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

/** Допуск обязательной операции без инициализации студенческого класса. */
public class ApiContract {
  public static void check(PlatformApiPrecheck.Api api) throws Exception {
    Class<?> study = api.type("Study");
    api.expect(
        Modifier.isPublic(study.getModifiers()), "public class Study без пакета", study.toString());
    var method = api.method("Study", "align", java.util.List.class,
        Modifier.PUBLIC | Modifier.STATIC, 0, String.class, int.class);
    api.expect(method.getGenericReturnType().equals(new java.lang.reflect.ParameterizedType() {
      public java.lang.reflect.Type[] getActualTypeArguments() {
        return new java.lang.reflect.Type[] {String.class};
      }
      public java.lang.reflect.Type getRawType() {
        return java.util.List.class;
      }
      public java.lang.reflect.Type getOwnerType() {
        return null;
      }
    }),
        "java.util.List<String> Study.align(String, int)",
        method.getGenericReturnType().getTypeName());
  }
}
