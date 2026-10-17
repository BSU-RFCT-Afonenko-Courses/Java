public class Counter {public static <T>int count(T[]a,Property<T>property){int n=0;for(T value:a)if(property.test(value))n++;return n;}}
