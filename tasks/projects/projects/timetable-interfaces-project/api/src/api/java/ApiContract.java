import java.lang.Exception;
import java.lang.String;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","model",Object.class,Modifier.PUBLIC|Modifier.STATIC,0,int.class);api.method("Study","call",Object.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class,String.class,Object[].class);api.compiles("Inputs","import java.lang.Object;\nimport java.lang.String;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(int specialization) {Object _apiValue=Study.model(specialization);}static  void call1(Object model,String operation,Object... arguments) {Object _apiValue=Study.call(model,operation,arguments);}}",true);}}
