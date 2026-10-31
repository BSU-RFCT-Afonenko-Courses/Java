import java.util.*;import java.util.function.*;import java.nio.file.Path;
public class RunFile {public static void main(String[]args){try{Study.select(Path.of(args[0]),Path.of(args[1]),args[2],args[3]);}catch(Exception failure){System.err.println("Ошибка файловой операции: "+failure.getMessage());}}}
