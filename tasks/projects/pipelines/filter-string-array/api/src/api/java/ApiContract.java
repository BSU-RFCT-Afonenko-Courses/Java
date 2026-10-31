import java.lang.Exception;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","filter",String[].class,Modifier.PUBLIC|Modifier.STATIC,0,String[].class,int.class);api.compiles("Inputs","import java.lang.String;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(String[] input,int threshold) {String[] _apiValue=Study.filter(input,threshold);}}",true);}}
