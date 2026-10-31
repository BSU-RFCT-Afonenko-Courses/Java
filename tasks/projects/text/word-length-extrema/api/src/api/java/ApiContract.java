import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Module;
import java.lang.ModuleLayer;
import java.lang.NoSuchMethodException;
import java.lang.Object;
import java.lang.Runtime;
import java.lang.String;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

/** Проверяет опубликованный launch API по metadata, не выполняя код решения. */
public class ApiContract {
  public static void check(PlatformApiPrecheck.Api api) throws Exception {
    check(api, false);
  }

  /** Фиксированный trusted entry для явно опубликованной формы String[]. */
  public static final class StringArrayMain {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
      ApiContract.check(api, false);
    }
  }

  private static void check(PlatformApiPrecheck.Api api, boolean stringArray) throws Exception {
    // Capability/provenance failures are infrastructure, not API mismatch.
    if (Runtime.version().feature() != 25)
      throw new IllegalStateException("Для проверки main требуется JDK 25");
    Class<?> finder = Class.forName("jdk.internal.misc.MethodFinder", false, null);
    Module base = Object.class.getModule();
    if (finder.getClassLoader() != null || finder.getModule() != base || !base.isNamed()
        || !"java.base".equals(base.getName())
        || ModuleLayer.boot().findModule("java.base").orElseThrow() != base)
      throw new IllegalStateException("Не подтверждён bootstrap selector JDK 25");
    Method selector = finder.getDeclaredMethod("findMainMethod", Class.class);
    if (selector.getDeclaringClass() != finder
        || selector.getReturnType() != Method.class || !Modifier.isPublic(selector.getModifiers())
        || !Modifier.isStatic(selector.getModifiers()))
      throw new IllegalStateException("Несовместимая capability selector JDK 25");
    Class<?> main = api.type("Main"); // Class.forName(..., false, studentLoader).
    Method selected = (Method) selector.invoke(null, main);
    api.expect(selected != null, "доступный для запуска main класса Main", "JDK 25 не выбрал main");
    if (stringArray)
      api.expect(selected.getReturnType()
              == void.class&& Arrays.equals(
                  selected.getParameterTypes(), new Class<?>[] {String[].class}),
          "выбранный JDK 25 Main.main(String[] args) → void", selected.toGenericString());
    if (!Modifier.isStatic(selected.getModifiers())) {
      api.expect(!Modifier.isAbstract(main.getModifiers()),
          "конкретный класс Main для instance main", "абстрактный Main");
      api.expect(!main.isMemberClass() || Modifier.isStatic(main.getModifiers()),
          "Main без внешнего экземпляра", "нестатический member class");
      try {
        var constructor = main.getDeclaredConstructor();
        api.expect(!Modifier.isPrivate(constructor.getModifiers()),
            "не private конструктор Main без параметров", "private конструктор Main()");
      } catch (NoSuchMethodException missing) {
        api.expect(false, "конструктор Main без параметров", "Main() отсутствует");
      }
    }
  }
}
