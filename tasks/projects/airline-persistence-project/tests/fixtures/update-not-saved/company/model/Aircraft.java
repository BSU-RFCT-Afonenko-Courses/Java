package company.model;
import java.io.Serializable;
public abstract class Aircraft implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final int seats;
    private final double payloadTonnes;
    private final int rangeKm;
    private final int fuelLitresPerHour;
    protected Aircraft(String id, int seats, double payloadTonnes, int rangeKm, int fuelLitresPerHour) {
        this.id = id;
        this.seats = seats;
        this.payloadTonnes = payloadTonnes;
        this.rangeKm = rangeKm;
        this.fuelLitresPerHour = fuelLitresPerHour;
    }
    public String getId() { return id; }
    public int getSeats() { return seats; }
    public double getPayloadTonnes() { return payloadTonnes; }
    public int getRangeKm() { return rangeKm; }
    public int getFuelLitresPerHour() { return fuelLitresPerHour; }
    public abstract String kind();
    public abstract Aircraft withSeats(int seats);
}
