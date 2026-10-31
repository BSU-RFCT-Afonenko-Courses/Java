import java.util.*;
/** Связь входов с реальной моделью и её операциями. */
public class Study {
 public static Object car(long id, String make, String model, int year, String color, double price, String registration){return new Car((int)id,make,model,year,color,price,registration);}
 public static Object secondConstructor(){return new Car(9,"A","M");}
 public static List<?> values(Object car){Car c=(Car)car;return List.of((long)c.id(),c.make(),c.model(),(long)c.year(),c.color(),c.price(),c.registration());}
 public static void edit(Object car, long id, String make, String model, int year, String color, double price, String registration){Car c=(Car)car;c.id((int)id);c.make(make);c.model(model);c.year(year);c.color(color);c.price(price);c.registration(registration);}
 public static String text(Object car){return car.toString();}
 public static Object[] array(Object... cars){return Arrays.copyOf(cars,cars.length,Car[].class);}
 public static Object[] byMake(Object[] cars, String make){return Car.make((Car[])cars,make);}
 public static Object[] olderThan(Object[] cars, String model, int currentYear, int age){return Car.older((Car[])cars,model,currentYear,age);}
 public static Object[] dearerThan(Object[] cars, int year, double price){return Car.dearer((Car[])cars,year,price);}
}
