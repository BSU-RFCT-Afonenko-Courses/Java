package flights.domain;
import java.io.Serializable;
public abstract class Airframe implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int number;
    private final int capacity;
    private final double tonnes;
    private final int distance;
    private final int burn;
    protected Airframe(int number, int capacity, double tonnes, int distance, int burn) {
        this.number = number;
        this.capacity = capacity;
        this.tonnes = tonnes;
        this.distance = distance;
        this.burn = burn;
    }
    public int number() { return number; }
    public int capacity() { return capacity; }
    public double tonnes() { return tonnes; }
    public int distance() { return distance; }
    public int burn() { return burn; }
    public abstract String category();
    public abstract Airframe resized(int capacity);
}
