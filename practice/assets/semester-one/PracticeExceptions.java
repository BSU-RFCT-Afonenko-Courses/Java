// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeExceptions {
    public static void main(String[] args) {
        var marks = new java.util.ArrayList<String>();
        try {
            marks.add("начало");
            Integer.parseInt("не число");
            marks.add("конец");
        } catch (NumberFormatException e) {
            marks.add("отказ");
        }
        System.out.println(marks);
    }
}
