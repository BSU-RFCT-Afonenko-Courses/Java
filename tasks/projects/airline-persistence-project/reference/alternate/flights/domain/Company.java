package flights.domain;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
public final class Company implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static String station = "default";
    private transient int selections;
    private List<Airframe> fleet;
    public Company() { fleet = new ArrayList<>(); }
    public Company(List<Airframe> planes) {
        fleet = new ArrayList<>(planes);
        var numbers = new HashSet<Integer>();
        for (Airframe plane : planes) if (!numbers.add(plane.number())) throw new IllegalArgumentException("Повтор номера");
    }
    public List<Airframe> fleet() { return List.copyOf(fleet); }
    public long places() { return fleet.stream().mapToLong(Airframe::capacity).sum(); }
    public double loadTonnes() { return fleet.stream().mapToDouble(Airframe::tonnes).sum(); }
    public List<Airframe> arranged() {
        fleet.sort((left, right) -> Integer.compare(right.distance(), left.distance()));
        return List.copyOf(fleet);
    }
    public Optional<Airframe> find(int low, int high) {
        return fleet.stream().filter(p -> p.burn() >= low && p.burn() <= high).findFirst();
    }
    public void capacity(int number, int value) {
        for (int i = 0; i < fleet.size(); i++) {
            if (fleet.get(i).number() == number) { fleet.set(i, fleet.get(i).resized(value)); return; }
        }
        throw new IllegalArgumentException("Неизвестный номер");
    }
    public void mark(int value) { selections = value; }
    public int marks() { return selections; }
    public static void station(String value) { station = value; }
    public static String station() { return station; }
    @Override public void writeExternal(ObjectOutput output) throws IOException {
        output.writeObject(new ArrayList<>(fleet));
        output.writeInt(selections);
        output.writeUTF(station);
    }
    @Override @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        fleet = (List<Airframe>) input.readObject();
        selections = input.readInt();
        station = input.readUTF();
    }
}
