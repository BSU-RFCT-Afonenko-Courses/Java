import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
public class RunFile {public static void main(String[]args){try{Study.process(Path.of(args[0]),Path.of(args[1]));}catch(Exception failure){System.err.println("Ошибка файловой операции: "+failure.getMessage());}}}
