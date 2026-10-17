package company.model;
public final class PassengerPlane extends Aircraft {
    private static final long serialVersionUID = 1L;
    public PassengerPlane(String id, int seats, double payloadTonnes, int rangeKm, int fuelLitresPerHour) {
        super(id, seats, payloadTonnes, rangeKm, fuelLitresPerHour);
    }
    @Override public String kind() { return "passenger"; }
    @Override public PassengerPlane withSeats(int seats) {
        return new PassengerPlane(getId(), seats, getPayloadTonnes(), getRangeKm(), getFuelLitresPerHour());
    }
}
