public class Study {
public static long[] compute(String[] args){long s=0,p=1;for(String arg:args){long n=Integer.parseInt(arg);s+=n;p*=n;}return new long[]{s,p};}
}
