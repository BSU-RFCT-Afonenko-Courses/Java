package sequence;
public class Values {
    public static int[] next() {var generator=new java.util.Random(42);return java.util.stream.IntStream.range(0,10).map(i->generator.nextInt(100)).toArray();}
}
