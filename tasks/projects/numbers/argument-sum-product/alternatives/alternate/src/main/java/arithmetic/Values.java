package arithmetic;
public class Values {
    public static long[] compute(String[] args) {
        long[] values=java.util.Arrays.stream(args).mapToLong(Long::parseLong).toArray();
        return new long[]{java.util.Arrays.stream(values).sum(),java.util.Arrays.stream(values).reduce(1,(a,b)->a*b)};
    }
}
