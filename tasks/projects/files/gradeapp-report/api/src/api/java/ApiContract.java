import java.lang.Class;
import java.lang.Error;
import java.lang.Exception;
import java.lang.NoSuchMethodException;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.*;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import org.prairielearn.api.PlatformApiPrecheck;

/** Проверка опубликованного API до компиляции зависимых JUnit-клиентов. */
public final class ApiContract {
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
        api.compiles("grade.PublishedClient", "package grade;\nimport grade.DataFormatException;\nimport grade.Main;\nimport grade.Reports;\nimport grade.Student;\nimport grade.StudentFiles;\nimport grade.StudentParser;\nimport java.lang.String;\n\nimport java.nio.file.Path;\nimport java.io.IOException;\nimport java.util.List;\nclass PublishedClient {\n    static Student parse(String line) {\n        try { return StudentParser.parse(line, 1L); }\n        catch (DataFormatException e) { return null; }\n    }\n    static List<Student> read(Path path) {\n        try { return StudentFiles.read(path); }\n        catch (IOException e) { return List.of(); }\n        catch (DataFormatException e) { return List.of(); }\n    }\n    static int run(Path path) {\n        try { return Main.run(path, 80); }\n        catch (IOException e) { return 4; }\n        catch (DataFormatException e) { return 3; }\n    }\n    static String report(List<Student> input) {\n        List<Student> output = Reports.selected(input, 80);\n        return Reports.line(output.getFirst());\n    }\n}\n", true);
        // Raw List не принимается за опубликованный List<Student>.
        // Эти клиенты отвергаются настоящим javac, без привязки к реализации коллекции.
        api.compiles("grade.WrongReadElement", "package grade;\nimport grade.StudentFiles;\nimport java.lang.Exception;\nimport java.lang.String;\n import java.util.List; import java.nio.file.Path;\nclass WrongReadElement { static void check(Path p) throws Exception {\n    List<String> strings = StudentFiles.read(p);\n}}\n", false);
        api.compiles("grade.WrongSelectedElement", "package grade;\nimport grade.Reports;\nimport grade.Student;\nimport java.lang.String;\n import java.util.List;\nclass WrongSelectedElement { static void check(List<Student> input) {\n    List<String> strings = Reports.selected(input, 80);\n}}\n", false);
        api.compiles("grade.WrongSelectedInput", "package grade;\nimport grade.Reports;\nimport java.lang.String;\n import java.util.List;\nclass WrongSelectedInput { static void check(List<String> input) {\n    Reports.selected(input, 80);\n}}\n", false);
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
