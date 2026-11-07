import java.lang.Object;
class TaskBinding {
    static Object vehicle(int p, int f, int m) {
        return new Vehicle(p, f, m);
    }
    static Object truck(int p, int f, int m, int c) {
        return new Truck(p, f, m, c);
    }
    static int cargo(Object x) {
        return ((Truck)x).getCargo();
    }
    static void cargo(Object x, int c) {
        ((Truck)x).putCargo(c);
    }
    static int[] state(Object x) {
        Vehicle v = (Vehicle)x;
        return new int[]{v.getPassengers(), v.getFuelcap(), v.getMpg()};
    }
    static int range(Object x) {
        return ((Vehicle)x).range();
    }
    static double fuel(Object x, int n) {
        return ((Vehicle)x).fuelNeeded(n);
    }
    static void setFuel(Object x, int v) {
        ((Vehicle)x).setFuelcap(v);
    }
    static void setPassengers(Object x, int v) {
        ((Vehicle)x).setPassengers(v);
    }
    static void setMpg(Object x, int v) {
        ((Vehicle)x).setMpg(v);
    }
}
