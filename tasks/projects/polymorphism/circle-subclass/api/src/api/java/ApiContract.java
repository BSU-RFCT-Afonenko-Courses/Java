import java.lang.Class;
import java.lang.NoSuchMethodException;
import java.lang.String;
import java.lang.reflect.Modifier;
import java.lang.Exception;
import org.prairielearn.api.PlatformApiPrecheck;
import org.prairielearn.api.PlatformApiPrecheck.Api;
public class ApiContract {public static void check(Api api)throws Exception{api.expect(Modifier.isAbstract(api.type("TwoDShape").getModifiers()),"TwoDShape abstract","type modifiers");api.method("TwoDShape","getWidth",double.class,Modifier.PUBLIC,0);api.method("TwoDShape","getHeight",double.class,Modifier.PUBLIC,0);api.method("TwoDShape","getName",String.class,Modifier.PUBLIC,0);api.method("TwoDShape","area",double.class,Modifier.PUBLIC,0);constructor(api,"Circle",double.class);api.compiles("Inputs","import java.lang.String;\nclass Inputs {void use(){TwoDShape x=new Circle(1);double w=x.getWidth(),h=x.getHeight(),a=x.area();String name=x.getName();}}",true);}private static void constructor(Api api,String name,Class<?>...parameters)throws Exception{try{var c=api.type(name).getDeclaredConstructor(parameters);api.expect(Modifier.isPublic(c.getModifiers()),name+" public constructor",c.toGenericString());}catch(NoSuchMethodException missing){api.expect(false,name+" constructor",missing.toString());}}}
