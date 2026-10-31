import java.lang.Object;
import java.lang.String;
import java.util.Objects;
public class Animal {private final String name;public Animal(String name){this.name=name;}protected String name(){return name;}public String voice(){return "Animal "+name;}public boolean equals(Object other){return other!=null&&getClass()==other.getClass()&&name.equals(((Animal)other).name);}public int hashCode(){return Objects.hash(getClass(),name);}public String toString(){return getClass().getSimpleName()+" "+name;}}
