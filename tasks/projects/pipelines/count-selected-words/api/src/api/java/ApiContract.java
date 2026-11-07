import java.lang.Exception;
import java.util.List;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","printCounts",void.class,Modifier.PUBLIC|Modifier.STATIC,0,String.class,List.class);api.compiles("Inputs","import java.lang.String;\nimport java.lang.Throwable;\nimport java.util.List;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(String text,List<String> queries) {Study.printCounts(text,queries);}}",true);}}
