import java.lang.Class;
import java.lang.NoSuchMethodException;
import java.lang.String;
import java.lang.reflect.Modifier;
import java.lang.Exception;
import org.prairielearn.api.PlatformApiPrecheck;
import org.prairielearn.api.PlatformApiPrecheck.Api;
public class ApiContract {public static void check(Api api)throws Exception{api.expect(api.type("ICharQ").isInterface(),"ICharQ interface","type kind");constructor(api,"DynQueue",int.class);api.compiles("Inputs","class Inputs {void call(){ICharQ q0=new DynQueue(2);q0.put('A');char value0=q0.get();}}",true);}private static void constructor(Api api,String name,Class<?>...parameters)throws Exception{try{var c=api.type(name).getDeclaredConstructor(parameters);api.expect(Modifier.isPublic(c.getModifiers()),name+" public constructor",c.toGenericString());}catch(NoSuchMethodException missing){api.expect(false,name+" constructor",missing.toString());}}}
