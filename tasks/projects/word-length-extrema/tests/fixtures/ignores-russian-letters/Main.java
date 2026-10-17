import java.util.ArrayList;
import java.util.regex.Pattern;
public class Main {
    public static void main(String[] args) {
        var matcher = Pattern.compile("[A-Za-z]+").matcher(args[0]);
        var words = new ArrayList<String>();
        int minimum = Integer.MAX_VALUE;
        int maximum = 0;
        while (matcher.find()) {
            String word = matcher.group();
            words.add(word);
            minimum = Math.min(minimum, word.length());
            maximum = Math.max(maximum, word.length());
        }
        if (words.isEmpty()) {
            System.out.println("Слов нет");
            return;
        }
        var longest = new ArrayList<String>();
        var shortest = new ArrayList<String>();
        for (String word : words) {
            if (word.length() == maximum) longest.add(word);
            if (word.length() == minimum) shortest.add(word);
        }
        System.out.println("Максимумы: " + String.join(" ", longest));
        System.out.println("Минимумы: " + String.join(" ", shortest));
    }
}
