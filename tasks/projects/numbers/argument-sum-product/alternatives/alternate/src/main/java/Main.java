class Main {
    void main(String[] args) {
        long[] values=Values.compute(args);
        System.out.printf(java.util.Locale.ROOT,"  %+022d\n\t%+022d  ",values[0],values[1]);
    }
}

class Values {
    public static long[] compute(String[] args) {
        long[] values=java.util.Arrays.stream(args).mapToLong(Long::parseLong).toArray();
        return new long[]{java.util.Arrays.stream(values).sum(),java.util.Arrays.stream(values).reduce(1,(a,b)->a*b)};
    }
}
