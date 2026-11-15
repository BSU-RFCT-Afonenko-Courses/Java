public class Main {
    void main(String[] args) {
        try {
            long[] r = Study.compute(args);
            System.out.println(r[0] + " " + r[1]);
        } finally {
            System.exit(7);
        }
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
