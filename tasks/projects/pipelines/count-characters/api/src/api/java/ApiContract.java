import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","count",Object.class,Modifier.PUBLIC|Modifier.STATIC,0,String.class);api.method("Study","frequencies",Map.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class);api.compiles("Inputs","import java.lang.Integer;\nimport java.lang.Number;\nimport java.lang.Object;\nimport java.lang.String;\nimport java.lang.Throwable;\nimport java.util.Map;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(String text) {Object _apiValue=Study.count(text);}static  void call1(Object result) {Map<Integer,? extends Number> _apiValue=Study.frequencies(result);}}",true);}}
