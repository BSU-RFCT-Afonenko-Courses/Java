public class SearchDemo {
    sealed interface SearchResult permits Found, Missing {}
    record Found(String title) implements SearchResult {}
    record Missing(String reason) implements SearchResult {}

    static String describe(SearchResult result) {
        return switch (result) {
            case Found found -> "Found: " + found.title();
            case Missing missing -> "Missing: " + missing.reason();
        };
    }
    public static void main(String[] args) {
        System.out.println(describe(new Found("Java")));
        System.out.println(describe(new Missing("Нет такого кода")));
        try { describe(null); }
        catch (NullPointerException ex) { System.out.println("Отсутствующий результат отвергнут"); }
    }
}
