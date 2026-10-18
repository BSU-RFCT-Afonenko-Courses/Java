package variant;
public class Example {
public long[] compute(String[] args){long s=0,p=1;for(String arg:args){long n=Long.parseLong(arg);s+=n;p*=n;}return new long[]{s,p};}
}
