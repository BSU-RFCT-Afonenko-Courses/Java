import java.lang.AssertionError;
import java.lang.Object;
import java.lang.String;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
class TaskBinding{static Object make(int i,String n){return Study.create(i,n);}static Object[]array(Object...v){Object[]a=Study.array(v);if(a==null||a.getClass().getComponentType()==Object.class||a.length!=v.length)throw new AssertionError("Массив модели");for(int i=0;i<v.length;i++)if(a[i]!=v[i])throw new AssertionError("Ссылки элементов");return a;}static Object[]run(Object[]a){return Study.distinct(a);}static int id(Object v){return Study.id(v);}static String name(Object v){return Study.name(v);}}
