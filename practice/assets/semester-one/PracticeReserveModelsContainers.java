// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeReserveModelsContainers {
    public static void main(String[] args) {
        var byLength = new java.util.TreeSet<String>(
            java.util.Comparator.comparingInt(String::length));
        byLength.add("кот");
        byLength.add("дом");
        System.out.println(byLength.size());
        System.out.println(byLength);
    }
}
