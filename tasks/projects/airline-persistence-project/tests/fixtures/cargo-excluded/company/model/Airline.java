package company.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Comparator;
public final class Airline implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String globalLabel = "default";
    private transient int menuCount;
    private final List<Aircraft> planes;
    public Airline(List<Aircraft> planes) {
        this.planes = new ArrayList<>(planes);
        var ids = new HashSet<String>();
        for (Aircraft plane : planes) {
            if (!ids.add(plane.getId())) throw new IllegalArgumentException("Повтор идентификатора");
        }
    }
    public List<Aircraft> planes() { return List.copyOf(planes); }
    public int capacity() { return planes.stream().filter(p -> !p.kind().equals("cargo")).mapToInt(Aircraft::getSeats).sum(); }
    public double payload() { return planes.stream().mapToDouble(Aircraft::getPayloadTonnes).sum(); }
    public List<Aircraft> byRange() {
        return planes.stream().sorted(Comparator.comparingInt(Aircraft::getRangeKm)).toList();
    }
    public List<Aircraft> matchingFuel(int low, int high) {
        return planes.stream().filter(p -> p.getFuelLitresPerHour() >= low && p.getFuelLitresPerHour() <= high).toList();
    }
    public void changeSeats(String id, int seats) {
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getId().equals(id)) {
                planes.set(i, planes.get(i).withSeats(seats));
                return;
            }
        }
        throw new IllegalArgumentException("Неизвестный идентификатор");
    }
    public void setMenuCount(int value) { menuCount = value; }
    public int getMenuCount() { return menuCount; }
    public static void setGlobalLabel(String value) { globalLabel = value; }
    public static String getGlobalLabel() { return globalLabel; }
}
