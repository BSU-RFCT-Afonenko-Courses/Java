import java.lang.Exception;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","function",Object.class,Modifier.PUBLIC|Modifier.STATIC,0);api.method("Study","apply",boolean.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class,int.class);api.compiles("Inputs","import java.lang.Object;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0() {Object _apiValue=Study.function();}static  void call1(Object function,int value) {boolean _apiValue=Study.apply(function,value);}}",true);}}
