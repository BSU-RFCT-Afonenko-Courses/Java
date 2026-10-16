import java.lang.reflect.*;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import org.prairielearn.api.PlatformApiPrecheck;

/** Проверка опубликованного API до компиляции зависимых JUnit-клиентов. */
public final class GradeAppApi {
    public static void check(PlatformApiPrecheck.Api api) throws Exception {
        Class<?> student = api.type("grade.Student");
        Class<?> format = api.type("grade.DataFormatException");
        api.expect(Modifier.isPublic(student.getModifiers()) && student.isRecord(),
            "предоставленный public record grade.Student", student.toGenericString());
        api.expect(Exception.class.isAssignableFrom(format) && !RuntimeException.class.isAssignableFrom(format),
            "предоставленный checked grade.DataFormatException", format.toGenericString());
        for (String name : List.of("grade.StudentParser", "grade.StudentFiles", "grade.Reports", "grade.Main")) {
            Class<?> type = api.type(name);
            api.expect(Modifier.isPublic(type.getModifiers()), "public " + name, type.toGenericString());
        }
        int visibleStatic = Modifier.PUBLIC | Modifier.STATIC;
        Method parse = api.method("grade.StudentParser", "parse", student, visibleStatic, 0, String.class, long.class);
        Method read = listMethod(api, "grade.StudentFiles", "read", Path.class);
        Method selected = listMethod(api, "grade.Reports", "selected", List.class, int.class);
        Method line = api.method("grade.Reports", "line", String.class, visibleStatic, 0, student);
        Method run = api.method("grade.Main", "run", int.class, Modifier.STATIC,
            Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED, Path.class, int.class);
        api.method("grade.Main", "main", void.class, visibleStatic, 0, String[].class);
        checked(api, parse, format);
        checked(api, read, IOException.class, format);
        checked(api, selected);
        checked(api, line);
        checked(api, run, IOException.class, format);
        // Обычный typed-клиент из grade имеет доступ к package-private run.
        // Отдельные catch допускают суженные checked-подтипы, а не только буквальный throws.
        api.compiles("grade.PublishedClient", """
            package grade;
            import java.nio.file.Path;
            import java.io.IOException;
            import java.util.List;
            class PublishedClient {
                static Student parse(String line) {
                    try { return StudentParser.parse(line, 1L); }
                    catch (DataFormatException e) { return null; }
                }
                static List<Student> read(Path path) {
                    try { return StudentFiles.read(path); }
                    catch (IOException e) { return List.of(); }
                    catch (DataFormatException e) { return List.of(); }
                }
                static int run(Path path) {
                    try { return Main.run(path, 80); }
                    catch (IOException e) { return 4; }
                    catch (DataFormatException e) { return 3; }
                }
                static String report(List<Student> input) {
                    List<Student> output = Reports.selected(input, 80);
                    return Reports.line(output.getFirst());
                }
            }
            """, true);
        // Raw List не принимается за опубликованный List<Student>.
        // Эти клиенты отвергаются настоящим javac, без привязки к реализации коллекции.
        api.compiles("grade.WrongReadElement", """
            package grade; import java.util.List; import java.nio.file.Path;
            class WrongReadElement { static void check(Path p) throws Exception {
                List<String> strings = StudentFiles.read(p);
            }}
            """, false);
        api.compiles("grade.WrongSelectedElement", """
            package grade; import java.util.List;
            class WrongSelectedElement { static void check(List<Student> input) {
                List<String> strings = Reports.selected(input, 80);
            }}
            """, false);
        api.compiles("grade.WrongSelectedInput", """
            package grade; import java.util.List;
            class WrongSelectedInput { static void check(List<String> input) {
                Reports.selected(input, 80);
            }}
            """, false);
    }

    private static Method listMethod(PlatformApiPrecheck.Api api, String owner, String name,
            Class<?>... parameters) throws Exception {
        Class<?> type = api.type(owner);
        Method method;
        try { method = type.getMethod(name, parameters); }
        catch (NoSuchMethodException e) {
            api.expect(false, "public static List<Student> " + owner + "." + name, e.toString());
            throw e;
        }
        api.expect(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())
                && List.class.isAssignableFrom(method.getReturnType()),
            "public static List<Student> " + owner + "." + name, method.toGenericString());
        return method;
    }

    private static void checked(PlatformApiPrecheck.Api api, Method method, Class<?>... allowed)
            throws Exception {
        for (Class<?> exception : method.getExceptionTypes()) {
            if (RuntimeException.class.isAssignableFrom(exception) || Error.class.isAssignableFrom(exception)) continue;
            boolean matches = false;
            for (Class<?> family : allowed) matches |= family.isAssignableFrom(exception);
            api.expect(matches, "checked throws только опубликованной области " + method.getName(),
                method.toGenericString());
        }
    }
}
