package airline;
import java.util.*;import java.io.Serializable;
public final class Airline implements Serializable {
 private static final long serialVersionUID=1L;private final List<Plane>park=new ArrayList<>();
 public Airline(Collection<?>planes){for(Object value:planes)park.add((Plane)value);}
 public List<Plane>planes(){return Collections.unmodifiableList(park);}
 public int totalSeats(){return park.stream().mapToInt(Plane::seats).sum();}public double totalTonnes(){return park.stream().mapToDouble(Plane::tonnes).sum();}
 public List<Plane>ordered(){park.sort(Comparator.comparingDouble(Plane::range));return planes();}
 public List<Plane>find(double low,double high){return park.stream().filter(p->p.fuel()>=low&&p.fuel()<=high).findFirst().map(List::of).orElseGet(List::of);}
}
