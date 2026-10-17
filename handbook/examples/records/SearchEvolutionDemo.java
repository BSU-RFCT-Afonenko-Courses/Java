import java.util.Objects;

public class SearchEvolutionDemo {
    sealed interface SearchResult permits Found, Missing, Ambiguous {}
    record Found(String title) implements SearchResult {
        Found { Objects.requireNonNull(title, "title"); }
    }
    record Missing(String reason) implements SearchResult {
        Missing { Objects.requireNonNull(reason, "reason"); }
    }
    record Ambiguous(int count) implements SearchResult {
        Ambiguous {
            if (count < 2) throw new IllegalArgumentException("Нужно хотя бы два совпадения");
        }
    }

    static String describe(SearchResult result) {
        return switch (result) {
            case Found found -> "Found: " + found.title();
            case Missing missing -> "Missing: " + missing.reason();
            case Ambiguous many -> "Matches: " + many.count();
        };
    }
    public static void main(String[] args) {
        System.out.println(describe(new Found("Java")));
        System.out.println(describe(new Missing("Нет такого кода")));
        System.out.println(describe(new Ambiguous(3)));
        try { new Ambiguous(1); }
        catch (IllegalArgumentException ex) { System.out.println("Один результат не неоднозначен"); }
        try { new Found(null); }
        catch (NullPointerException ex) { System.out.println("Название обязательно"); }
    }
}
