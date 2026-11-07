class Main {
    void main() {
        for(int value:Values.next())System.out.println("  "+value+"  ");
        System.out.println();
        int[] again=Values.next();
        for(int i=0;i<again.length;i++)System.out.print((i==0?"  ":"\t")+again[i]);
        System.out.print("  ");
    }
}

class Values {
    public static int[] next() {var generator=new java.util.Random(42);return java.util.stream.IntStream.range(0,10).map(i->generator.nextInt(100)).toArray();}
}
