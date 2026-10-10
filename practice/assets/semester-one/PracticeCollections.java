// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeCollections {
    public static void main(String[] args) {
        var source = new java.util.ArrayList<>(
            java.util.List.of("синий", "белый"));
        var view = java.util.Collections.unmodifiableList(source);
        var snapshot = java.util.List.copyOf(source);
        source.add("серый");
        System.out.println(view.size());
        System.out.println(snapshot.size());
    }
}
