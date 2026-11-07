import java.lang.Exception;
import java.util.List;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{api.method("Study","save",void.class,Modifier.PUBLIC|Modifier.STATIC,0,List.class,Path.class);api.method("Study","select",void.class,Modifier.PUBLIC|Modifier.STATIC,0,Path.class,Path.class,String.class,String.class);api.compiles("Inputs","import java.lang.Exception;\nimport java.lang.String;\nimport java.lang.Throwable;\nimport java.util.List;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static  void call0(List<String> records,Path input) throws Exception {Study.save(records,input);}static  void call1(Path input,Path output,String k,String j) throws Exception {Study.select(input,output,k,j);}}",true);}}
