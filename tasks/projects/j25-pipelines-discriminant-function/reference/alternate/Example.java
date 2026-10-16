package variant;
public class Example {
@FunctionalInterface interface Quadratic {double value(double a,double b,double c);}
public double discriminant(double a,double b,double c){Quadratic f=(x,y,z)->y*y-4*x*z;return f.value(a,b,c);}
public void demonstrate(){System.out.println(discriminant(1,-3,2));}
}
