import java.lang.Object;
import java.lang.Class;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import org.prairielearn.api.PlatformApiPrecheck.Api;
import java.util.function.Function;
import java.util.function.Predicate;
import java.lang.Exception;
import java.util.*;import java.util.function.*;import java.nio.file.Path;
import org.prairielearn.api.PlatformApiPrecheck;import java.lang.reflect.Modifier;
public class ApiContract {public static void check(PlatformApiPrecheck.Api api)throws Exception{var method=api.method("Study","ternaryOperator",Function.class,Modifier.PUBLIC|Modifier.STATIC,0,Predicate.class,Function.class,Function.class);var vars=method.getTypeParameters();api.expect(vars.length==2,"два generic параметра","arity="+vars.length);Type a=vars[0],b=vars[1];Type[]params=method.getGenericParameterTypes();api.expect(plain(method.getGenericReturnType(),Function.class,a,b)&&unary(params[0],Predicate.class,a,Object.class)&&binary(params[1],Function.class,a,b)&&binary(params[2],Function.class,a,b),"<T,R> Function<T,R> / contravariant inputs / covariant outputs",method.toGenericString());api.compiles("Inputs","import java.lang.Throwable;\nimport java.util.function.Function;\nimport java.util.function.Predicate;\nimport java.util.*;import java.util.function.*;import java.nio.file.Path;\nclass Inputs {static <T,R> void call0(Predicate<? super T> condition,Function<? super T,? extends R> ifTrue,Function<? super T,? extends R> ifFalse) {Function<T,R> _apiValue=Study.ternaryOperator(condition,ifTrue,ifFalse);}}",true);}private static boolean plain(Type t,Class<?>raw,Type...args){return t instanceof ParameterizedType p&&p.getRawType().equals(raw)&&Arrays.equals(p.getActualTypeArguments(),args);}private static boolean bound(Type t,Type lower,Type upper){return t instanceof WildcardType w&&Arrays.equals(w.getLowerBounds(),lower==null?new Type[0]:new Type[]{lower})&&Arrays.equals(w.getUpperBounds(),new Type[]{upper});}private static boolean unary(Type t,Class<?>raw,Type lower,Type upper){return t instanceof ParameterizedType p&&p.getRawType().equals(raw)&&p.getActualTypeArguments().length==1&&bound(p.getActualTypeArguments()[0],lower,upper);}private static boolean binary(Type t,Class<?>raw,Type input,Type output){return t instanceof ParameterizedType p&&p.getRawType().equals(raw)&&p.getActualTypeArguments().length==2&&bound(p.getActualTypeArguments()[0],input,Object.class)&&bound(p.getActualTypeArguments()[1],null,output);}}
