public class Study {
@FunctionalInterface interface Quadratic {double value(double a,double b,double c);}
public static double discriminant(double a,double b,double c){Quadratic f=(x,y,z)->y*y-4*x*z;return f.value(a,b,c);}
public static void main(String[] args){System.out.println(discriminant(1,-3,2));}
}
