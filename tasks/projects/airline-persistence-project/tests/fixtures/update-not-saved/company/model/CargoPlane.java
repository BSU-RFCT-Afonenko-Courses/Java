package company.model;
public final class CargoPlane extends Aircraft {
    private static final long serialVersionUID = 1L;
    public CargoPlane(String id, int seats, double payloadTonnes, int rangeKm, int fuelLitresPerHour) {
        super(id, seats, payloadTonnes, rangeKm, fuelLitresPerHour);
    }
    @Override public String kind() { return "cargo"; }
    @Override public CargoPlane withSeats(int seats) {
        return new CargoPlane(getId(), seats, getPayloadTonnes(), getRangeKm(), getFuelLitresPerHour());
    }
}
