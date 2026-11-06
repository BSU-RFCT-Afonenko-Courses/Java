package airline;
public final class Passenger extends Plane {
    public Passenger(String id, int seats, double tonnes, double range, double fuel) {
        super(id, seats, tonnes, range, fuel);
        // TODO: особенности вида самолёта.
    }
    public int kind() { throw new UnsupportedOperationException("TODO"); }
}
