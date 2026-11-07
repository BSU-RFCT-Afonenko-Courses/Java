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

class Car {private int id,year;private String make,model,color,registration;private double price;public Car(int id,String make,String model,int year,String color,double price,String registration){this.id=id;this.make=make;this.model=model;this.year=year;this.color=color;this.price=price;this.registration=registration;}public Car(int id,String make,String model){this(id,make,model,2000,"black",0,"X");}public int id(){return id;}public void id(int x){id=x;}public int year(){return year;}public void year(int x){year=x;}public String make(){return make;}public void make(String x){make=x;}public String model(){return model;}public void model(String x){model=x;}public String color(){return color;}public void color(String x){color=x;}public double price(){return price;}public void price(double x){price=x;}public String registration(){return registration;}public void registration(String x){registration=x;}public String toString(){return id+":"+make+":"+model+":"+year+":"+color+":"+price+":"+registration;}public static Car[] make(Car[]a,String s){return java.util.Arrays.stream(a).filter(c->c.make.equals(s)).toArray(Car[]::new);}public static Car[] older(Car[]a,String s,int now,int n){return java.util.Arrays.stream(a).filter(c->c.model.equals(s)&&2025-c.year>n).toArray(Car[]::new);}public static Car[] dearer(Car[]a,int y,double p){return java.util.Arrays.stream(a).filter(c->c.year==y&&c.price>p).toArray(Car[]::new);}}
