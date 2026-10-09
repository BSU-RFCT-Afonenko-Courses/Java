import java.util.Objects;

public final class UnicodeSlice {
    private UnicodeSlice() {}

    public static String slice(String text, int from, int to) {
        Objects.requireNonNull(text, "text");
        int count = text.codePointCount(0, text.length());
        if (from < 0 || to < from || to > count) {
            throw new IndexOutOfBoundsException("Invalid code-point range");
        }
        int start = text.offsetByCodePoints(0, from);
        int end = text.offsetByCodePoints(start, to - from);
        return text.substring(start, end);
    }
}
