import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
class CheckedFunction {
    Function<Path, String> read = Files::readString;
}
