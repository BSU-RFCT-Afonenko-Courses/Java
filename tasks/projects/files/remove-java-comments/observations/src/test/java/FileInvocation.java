import java.io.File;
import java.lang.AssertionError;
import java.lang.Exception;
import java.lang.String;
import java.nio.file.Path;import java.time.Duration;import java.util.List;import java.io.IOException;import coursesupport.JavaProcess;
final class FileInvocation {
 private static JavaProcess.Result last;
 static void run(String...args)throws Exception{last=null;JavaProcess.Result r=JavaProcess.run(Path.of(Path.of(ProcessPort.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString()+File.pathSeparator+Path.of(Study.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString()),"ProcessPort","",List.of(args),null,Duration.ofSeconds(10),65536);last=r;if(r.stderr().contains("COURSE_PORT_FAILURE="))throw new IOException(r.stderr());}
 interface Action {void run()throws Exception;}
 static void refused(Action action)throws Exception{last=null;try{action.run();}catch(IOException failure){return;}if(last==null||last.stdout().isBlank()&&last.stderr().isBlank())throw new AssertionError("Отказ без причины");}
}
