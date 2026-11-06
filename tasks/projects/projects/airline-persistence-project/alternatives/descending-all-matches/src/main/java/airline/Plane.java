package airline;
import java.io.Serializable;

public abstract class Plane implements Serializable {
 private static final long serialVersionUID=1L;
 private final String id;private int seats;private final double tonnes,range,fuel;
 public static int instances;public transient double cached;
 protected Plane(String id,int seats,double tonnes,double range,double fuel){this.id=id;this.seats=seats;this.tonnes=tonnes;this.range=range;this.fuel=fuel;instances++;}
 public String id(){return id;}public int seats(){return seats;}public void seats(int value){seats=value;}public double tonnes(){return tonnes;}public double range(){return range;}public double fuel(){return fuel;}public abstract int kind();
 public String toString(){return id+" "+getClass().getSimpleName()+" "+seats+" мест "+tonnes+" т "+range+" км "+fuel+" л/ч";}
}
