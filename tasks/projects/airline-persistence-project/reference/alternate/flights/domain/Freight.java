package flights.domain;
public final class Freight extends Airframe {
    private static final long serialVersionUID = 1L;
    public Freight(int number, int capacity, double tonnes, int distance, int burn) {
        super(number, capacity, tonnes, distance, burn);
    }
    @Override public String category() { return "cargo"; }
    @Override public Freight resized(int capacity) { return new Freight(number(), capacity, tonnes(), distance(), burn()); }
}
