import java.lang.Exception;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","process",void.class,Modifier.PUBLIC|Modifier.STATIC,0,Path.class,Path.class,int.class,int.class);api.compiles("Inputs","import java.lang.Exception;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(Path input,Path output,int m,int n) throws Exception {Study.process(input,output,m,n);}}",true);}}
