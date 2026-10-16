package variant;
import java.nio.file.*;import java.nio.charset.StandardCharsets;import java.io.*;import java.util.*;import java.util.stream.*;
class RunFile {void main(String[] args){try{new Example().process(Path.of(args[0]),Path.of(args[2]),args[1]);}catch(IOException e){System.err.println("Ошибка файла: "+e.getMessage());}}}
