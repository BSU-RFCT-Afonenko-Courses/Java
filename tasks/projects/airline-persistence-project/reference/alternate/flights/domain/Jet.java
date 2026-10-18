package flights.domain;
public final class Jet extends Airframe {
    private static final long serialVersionUID = 1L;
    public Jet(int number, int capacity, double tonnes, int distance, int burn) {
        super(number, capacity, tonnes, distance, burn);
    }
    @Override public String category() { return "passenger"; }
    @Override public Jet resized(int capacity) { return new Jet(number(), capacity, tonnes(), distance(), burn()); }
}
