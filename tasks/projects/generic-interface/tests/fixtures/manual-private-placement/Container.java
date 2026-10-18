public class Container{private static class MyClass<A,B extends A>implements IGenIF<A,B>{}public static IGenIF<Number,Integer>demonstrate(){return new MyClass<Number,Integer>();}}
