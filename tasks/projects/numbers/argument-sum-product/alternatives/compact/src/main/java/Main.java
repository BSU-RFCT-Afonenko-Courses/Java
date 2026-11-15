class Main {
    void main(String[] args) {
        long[] r = Study.compute(args);
        System.out.println(r[0] + " " + r[1]);
    }
}

class Study {
    public static long[] compute(String[] args) {
        long s = 0, p = 1;
        for (String arg : args) {
            long n = Long.parseLong(arg);
            s += n;
            p *= n;
        }
        return new long[] {s, p};
    }
}
