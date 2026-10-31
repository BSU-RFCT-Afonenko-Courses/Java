import java.lang.Exception;
import java.nio.file.Path;
class TaskBinding {
static void run(Path a,Path b)throws Exception{FileInvocation.run(a.toString(),b.toString());}
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}}
