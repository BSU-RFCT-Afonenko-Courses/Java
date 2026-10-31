import java.lang.Exception;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","entryPoint",Class.class,Modifier.PUBLIC|Modifier.STATIC,0);api.compiles("Inputs","import java.lang.Class;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0() {Class<?> _apiValue=Study.entryPoint();}}",true);}}
