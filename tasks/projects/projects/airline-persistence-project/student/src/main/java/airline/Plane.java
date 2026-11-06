package airline;
import java.io.Serializable;
/** Модель самолёта: состояние и поведение реализуйте по условию. */
public abstract class Plane implements Serializable {
    protected Plane(String id, int seats, double tonnes, double range, double fuel) {
        throw new UnsupportedOperationException("TODO: состояние самолёта");
    }
    public String id() { throw new UnsupportedOperationException("TODO"); }
    public int seats() { throw new UnsupportedOperationException("TODO"); }
    public void seats(int value) { throw new UnsupportedOperationException("TODO"); }
    public double tonnes() { throw new UnsupportedOperationException("TODO"); }
    public double range() { throw new UnsupportedOperationException("TODO"); }
    public double fuel() { throw new UnsupportedOperationException("TODO"); }
    public abstract int kind();
}
