import java.lang.Exception;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","select",String[].class,Modifier.PUBLIC|Modifier.STATIC,0,String[].class,char.class,int.class);api.compiles("Inputs","import java.lang.String;\nimport java.lang.Throwable;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(String[] input,char initial,int n) {String[] _apiValue=Study.select(input,initial,n);}}",true);}}
