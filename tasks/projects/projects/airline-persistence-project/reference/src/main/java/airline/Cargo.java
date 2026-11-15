package airline;

public final class Cargo extends Plane {
    private static final long serialVersionUID = 1L;

    public Cargo(String id, int seats, double tonnes, double range, double fuel) {
        super(id, seats, tonnes, range, fuel);
    }

    public int kind() {
        return 1;
    }
}
