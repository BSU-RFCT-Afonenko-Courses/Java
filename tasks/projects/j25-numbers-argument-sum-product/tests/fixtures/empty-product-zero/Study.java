public class Study {
public static long[] compute(String[] args){long s=0,p=0;for(String arg:args){long n=Long.parseLong(arg);s+=n;p*=n;}return new long[]{s,p};}
}
