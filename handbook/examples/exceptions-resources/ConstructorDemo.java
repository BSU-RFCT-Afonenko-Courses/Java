import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConstructorDemo {
    static final class Limits {
        final int maximum;
        Limits(int maximum, List<String> events) {
            events.add("начало создания");
            if (maximum < 0) throw new IllegalArgumentException("maximum < 0");
            this.maximum = maximum;
        }
    }

    static class TextSnapshot {
        final String text;
        TextSnapshot(Path path) throws IOException {
            text = Files.readString(path, StandardCharsets.UTF_8);
        }
    }

    static final class NamedSnapshot extends TextSnapshot {
        NamedSnapshot(Path path) throws IOException { super(path); }
    }

    public static void main(String[] args) {
        var events = new ArrayList<String>();
        Limits saved = null;
        try {
            saved = new Limits(-1, events);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(saved);
        System.out.println(events);
        if (args.length == 1) {
            try {
                var snapshot = new NamedSnapshot(Path.of(args[0]));
                System.out.print(snapshot.text);
            } catch (IOException ex) {
                System.out.println("создание снимка не завершено: " + ex.getClass().getSimpleName());
            }
        }
    }
}
