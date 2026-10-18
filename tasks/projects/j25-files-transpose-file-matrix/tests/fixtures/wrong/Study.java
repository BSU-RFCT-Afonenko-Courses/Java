public class Study {
public static void process(java.nio.file.Path in,java.nio.file.Path out)throws java.io.IOException{java.nio.file.Files.createDirectories(out.getParent());java.nio.file.Files.writeString(out,"",java.nio.charset.StandardCharsets.UTF_8);}
}
