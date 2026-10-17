import java.io.IOException;
import java.nio.file.Path;

public class ScoreFileDemo {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Укажите путь к файлу с первым баллом");
            return;
        }
        try {
            var result = FileDemo.firstScore(Path.of(args[0]));
            String message = switch (result) {
                case ExceptionDemo.Score(var value) -> "балл: " + value;
                case ExceptionDemo.ScoreFailure(var problem) -> "ошибка данных: " + problem;
            };
            System.out.println(message);
        } catch (IOException failure) {
            System.out.println("ошибка чтения: " + failure.getClass().getSimpleName());
        }
    }
}
