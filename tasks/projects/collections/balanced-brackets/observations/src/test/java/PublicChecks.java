import java.lang.Throwable;
import java.lang.AssertionError;
import java.lang.String;
import java.util.*;

public class PublicChecks {
    public record Observation(String actual, boolean ok) {}
    private static final class CheckFailure extends AssertionError {
        @java.io.Serial
        private static final long serialVersionUID = 1L;

        CheckFailure(String message) {
            super(message);
        }
    }
    public static void checkNormal(Observation o) {
        if (!o.ok())
            throw new CheckFailure("Ожидается: true для ([]{})."
                + "; получено: " + o.actual());
    }
    public static void checkBoundary(Observation o) {
        if (!o.ok())
            throw new CheckFailure("Ожидается: true для пустой строки; false для )."
                + "; получено: " + o.actual());
    }

    public static Observation observeNormal() throws Throwable {
        String input = "([]{})";
        boolean r = TaskBinding.valid(input);
        return new Observation("вход=" + input + "; результат=" + r, r && input.equals("([]{})"));
    }
    public static Observation observeBoundary() throws Throwable {
        boolean empty = TaskBinding.valid(""), close = TaskBinding.valid(")");
        return new Observation("пустая строка=" + empty + "; закрывающая скобка=" + close, empty && !close);
    }
}
