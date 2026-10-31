import java.lang.Class;
import java.lang.NoSuchMethodException;
import java.lang.String;
import java.lang.reflect.Modifier;
import java.lang.Exception;
import org.prairielearn.api.PlatformApiPrecheck;
import org.prairielearn.api.PlatformApiPrecheck.Api;
public class ApiContract {public static void check(Api api)throws Exception{api.expect(api.type("IVehicle").isInterface(),"IVehicle interface","type kind");constructor(api,"Vehicle",int.class,int.class,int.class);api.compiles("Inputs","class Inputs {void use(){IVehicle x=new Vehicle(2,10,25);int p=x.getPassengers(),f=x.getFuelcap(),m=x.getMpg(),r=x.range();double fuel=x.fuelNeeded(1);x.setPassengers(0);x.setFuelcap(1);x.setMpg(1);}}",true);}private static void constructor(Api api,String name,Class<?>...parameters)throws Exception{try{var c=api.type(name).getDeclaredConstructor(parameters);api.expect(Modifier.isPublic(c.getModifiers()),name+" public constructor",c.toGenericString());}catch(NoSuchMethodException missing){api.expect(false,name+" constructor",missing.toString());}}}
