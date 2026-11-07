import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object create(){return new Variant();}
 public static void add(Object storage, double value){((Variant)storage).insert(value);}
 public static void remove(Object storage, double value){((Variant)storage).erase(value);}
 public static OptionalDouble nearest(Object storage, double query){return ((Variant)storage).find(query);}
}

class Variant {private final List<Double> values=new LinkedList<>();public void insert(double x){for(double v:values)if(Double.compare(v,x)==0)return;values.add(x);}public void erase(double x){var it=values.iterator();while(it.hasNext())if(Double.compare(it.next(),x)==0){it.remove();return;}}public OptionalDouble find(double query){boolean found=false;double best=0,dist=0;for(double v:values){double d=Math.abs(v-query);if(!found||d<dist||d==dist&&Double.compare(v,best)<0){found=true;best=v;dist=d;}}return found?OptionalDouble.of(best):OptionalDouble.empty();}}
