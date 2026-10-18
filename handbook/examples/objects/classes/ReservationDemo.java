public class ReservationDemo {
    public static void main(String[] args) {
        Reservation room = new Reservation(30);
        Reservation same = room;
        room.reserve(8);
        System.out.println(room.available());
        try {
            same.reserve(25);
        } catch (IllegalArgumentException ex) {
            System.out.println(room.available());
        }

        Reservation first = new Reservation(30);
        Reservation second = new Reservation(30);
        first.reserve(8);
        System.out.println(first.available());
        System.out.println(second.available());
    }
}
