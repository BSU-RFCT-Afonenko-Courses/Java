package equations;
public class Solver {
    public static double[] roots(long a,long b,long c) {
        long discriminant=b*b-4*a*c;
        if(discriminant<0)return new double[0];
        if(discriminant==0)return new double[]{-b/(2.0*a)};
        double q=-0.5*(b+Math.copySign(Math.sqrt(discriminant),b));
        return new double[]{q/a,c/q};
    }
}
