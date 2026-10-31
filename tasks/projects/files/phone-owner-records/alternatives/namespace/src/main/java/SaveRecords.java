import java.lang.Exception;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import support.FileDestination;
class SaveRecords {void main(String[] args)throws Exception{FileDestination.create(Path.of(args[0]));Files.write(Path.of(args[0]),Arrays.asList("291234567;Анна","331234567;Борис","441234567;Вера"),StandardCharsets.UTF_8);Study.process(Path.of(args[0]),Path.of(args[1]),"29","33");}}
