import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
class BrokenCopy { void main(String[] args) throws Exception {
        Path source=Path.of(args[0]),target=Path.of(args[1]);
        Files.write(target,new byte[0]);
        byte[] data=Files.readAllBytes(source);
        for(int i=0;i<data.length;i++)if(data[i]==32)data[i]=45;
        Files.write(target,data);

}}
