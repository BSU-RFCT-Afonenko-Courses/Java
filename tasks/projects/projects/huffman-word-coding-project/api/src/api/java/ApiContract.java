import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","encode",Object.class,Modifier.PUBLIC|Modifier.STATIC,0,String.class);api.method("Study","frequencies",Map.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class);api.method("Study","codes",Map.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class);api.method("Study","bits",String.class,Modifier.PUBLIC|Modifier.STATIC,0,Object.class);api.compiles("Inputs","import java.lang.Long;\nimport java.lang.Object;\nimport java.lang.String;\nimport java.lang.Throwable;\nimport java.util.Map;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(String text) {Object _apiValue=Study.encode(text);}static  void call1(Object result) {Map<String,Long> _apiValue=Study.frequencies(result);}static  void call2(Object result) {Map<String,String> _apiValue=Study.codes(result);}static  void call3(Object result) {String _apiValue=Study.bits(result);}}",true);}}
