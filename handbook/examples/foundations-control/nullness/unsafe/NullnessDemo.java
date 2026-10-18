import org.checkerframework.checker.nullness.qual.Nullable;

public class NullnessDemo {
    static int length(@Nullable String text) {
        return text.length();
    }
    public static void main(String[] args) {
        System.out.println(length(null));
    }
}
