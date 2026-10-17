import java.lang.reflect.*;
import java.util.function.*;
import org.prairielearn.api.PlatformApiPrecheck.Api;
public class PublishedApi {
static boolean parameterized(Type t,Class<?> raw,Type... args){return t instanceof ParameterizedType p && p.getRawType().equals(raw) && java.util.Arrays.equals(p.getActualTypeArguments(),args);}
static boolean lower(Type t,Type bound){return t instanceof WildcardType w && java.util.Arrays.equals(w.getLowerBounds(),new Type[]{bound}) && java.util.Arrays.equals(w.getUpperBounds(),new Type[]{Object.class});}
static boolean upper(Type t,Type bound){return t instanceof WildcardType w && w.getLowerBounds().length==0 && java.util.Arrays.equals(w.getUpperBounds(),new Type[]{bound});}
static boolean contract(Method m){
 if(!m.getName().equals("ternaryOperator")||m.getTypeParameters().length!=2||m.getParameterCount()!=3)return false;
 var v=m.getTypeParameters();for(var t:v)if(!java.util.Arrays.equals(t.getBounds(),new Type[]{Object.class}))return false;
 if(!parameterized(m.getGenericReturnType(),Function.class,v[0],v[1]))return false;
 var p=m.getGenericParameterTypes();if(!(p[0] instanceof ParameterizedType a)||a.getRawType()!=Predicate.class||!lower(a.getActualTypeArguments()[0],v[0]))return false;
 for(int i=1;i<=2;i++){if(!(p[i] instanceof ParameterizedType f)||f.getRawType()!=Function.class)return false;var x=f.getActualTypeArguments();if(!lower(x[0],v[0])||!upper(x[1],v[1]))return false;}
 return true;
}
public static void check(Api api)throws Exception {
 var owner=api.type("variant.Example");boolean found=java.util.Arrays.stream(owner.getDeclaredMethods()).anyMatch(PublishedApi::contract);
 api.expect(found,"неограниченный generic ternaryOperator с опубликованными variance и Function<T,R>",java.util.Arrays.toString(owner.getDeclaredMethods()));
 api.compiles("VarianceClient", "import java.util.function.*; class VarianceClient {void f(){Predicate<Object> c=x->true;Function<Object,Integer> y=x->1;Function<CharSequence,Double> n=x->2.0;Function<String,Number> f=new variant.Example().ternaryOperator(c,y,n);}}",true);
 api.compiles("WrongReturnClient", "import java.util.function.*; class WrongReturnClient {void f(){Function<String,Integer> f=new variant.Example().ternaryOperator(String::isEmpty,s->\"wrong\",s->1);}}",false);
}
}
