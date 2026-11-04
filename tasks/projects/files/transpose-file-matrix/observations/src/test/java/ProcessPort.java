import java.lang.Exception;
import java.lang.String;
import java.nio.file.Path;

public class ProcessPort {
    public static void main(String[] args) throws Exception {
        Study.process(Path.of(args[0]), Path.of(args[1]));
    }
}
