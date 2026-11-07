import java.io.DataOutputStream;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.*;import java.io.*;import java.util.List;
public class AirlineLoadPort {public static void main(String[]args)throws Exception{Object company=Study.load(Path.of(args[0]));List<?>planes=Study.planes(company);try(DataOutputStream out=new DataOutputStream(Files.newOutputStream(Path.of(args[1])))){out.writeInt(planes.size());for(Object plane:planes){out.writeUTF(Study.fixtureId(plane));out.writeInt(Study.kind(plane));out.writeInt(Study.seats(plane));out.writeDouble(Study.tonnes(plane));out.writeDouble(Study.range(plane));out.writeDouble(Study.fuel(plane));}}}}
