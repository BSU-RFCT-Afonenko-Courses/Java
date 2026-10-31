import java.lang.Exception;
import java.lang.String;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","function",Object.class,Modifier.PUBLIC|Modifier.STATIC,0);api.method("Study","apply",String.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class,String.class,String.class);api.compiles("Inputs","import java.lang.Object;\nimport java.lang.String;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0() {Object _apiValue=Study.function();}static  void call1(Object function,String first,String second) {String _apiValue=Study.apply(function,first,second);}}",true);}}
