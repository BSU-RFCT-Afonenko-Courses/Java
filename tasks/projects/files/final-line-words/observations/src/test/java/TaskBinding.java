import java.lang.Exception;
import java.lang.Integer;
import java.nio.file.Path;
class TaskBinding {
static void run(Path a,Path b,int m,int n)throws Exception{FileInvocation.run(a.toString(),b.toString(),Integer.toString(m),Integer.toString(n));}
static void refused(FileInvocation.Action action)throws Exception{FileInvocation.refused(action);}}
