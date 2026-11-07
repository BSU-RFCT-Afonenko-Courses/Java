package airline;
public final class Cargo extends Plane {
    public Cargo(String id, int seats, double tonnes, double range, double fuel) {
        super(id, seats, tonnes, range, fuel);
        // TODO: особенности вида самолёта.
    }
    public int kind() { throw new UnsupportedOperationException("TODO"); }
}
