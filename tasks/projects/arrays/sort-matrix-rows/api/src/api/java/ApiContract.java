import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.prairielearn.api.PlatformApiPrecheck;

/** Проверяет опубликованный launch API по metadata, не выполняя код решения. */
public class ApiContract {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        check(api, true);
    }

    /** Фиксированный trusted entry для явно опубликованной формы String[]. */
    public static final class StringArrayMain {
        public static void check(PlatformApiPrecheck.Api api) throws Exception {
            ApiContract.check(api, true);
        }
    }

    // JLS 12.1.4: String[] имеет приоритет; доступны не private методы void main.
    private static Method findMain(Class<?> type) {
        for (Class<?>[] parameters : new Class<?>[][]{{String[].class}, {}}) {
            for (Class<?> owner = type; owner != null; owner = owner.getSuperclass()) {
                try {
                    Method method = owner.getDeclaredMethod("main", parameters);
                    int access = method.getModifiers();
                    if (method.getReturnType() != void.class || Modifier.isPrivate(access)) continue;
                    if (!Modifier.isPublic(access) && !Modifier.isProtected(access)) {
                        boolean inherited = true;
                        for (Class<?> child = type; child != owner; child = child.getSuperclass())
                            if (!child.getPackageName().equals(owner.getPackageName())) inherited = false;
                        if (!inherited) continue;
                    }
                    return method;
                } catch (NoSuchMethodException absent) { }
            }
            try {
                Method method = type.getMethod("main", parameters);
                if (method.getReturnType() == void.class) return method;
            } catch (NoSuchMethodException absent) { }
        }
        return null;
    }

    private static void check(PlatformApiPrecheck.Api api, boolean stringArray) throws Exception {
        Class<?> main = api.type("Main");
        Method selected = findMain(main);
        api.expect(selected != null, "доступный для запуска main класса Main", "JDK 25 не выбрал main");
        if (stringArray)
            api.expect(selected.getReturnType() == void.class
                    && java.util.Arrays.equals(selected.getParameterTypes(), new Class<?>[]{String[].class}),
                "выбранный JDK 25 Main.main(String[] args) → void", selected.toGenericString());
        if (!Modifier.isStatic(selected.getModifiers())) {
            api.expect(!Modifier.isAbstract(main.getModifiers()), "конкретный класс Main для instance main", "абстрактный Main");
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
