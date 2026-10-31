import java.lang.Object;
import java.lang.String;
public class Study{public static Object create(int k,String n){return switch(k){case 0->new Animal(n);case 1->new Dog(n);default->new Puppy(n);};}public static String voice(Object a){return ((Animal)a).voice();}public static String name(Object a){return ((Animal)a).name();}public static String action(Object a,int k){return switch(k){case 0->((Dog)a).run();case 1->((Dog)a).bite();default->((Puppy)a).jump();};}}
