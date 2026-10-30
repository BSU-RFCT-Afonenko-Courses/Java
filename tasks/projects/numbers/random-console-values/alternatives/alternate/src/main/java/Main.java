class Main {
    void main() {
        for(int value:sequence.Values.next())System.out.println("  "+value+"  ");
        System.out.println();
        int[] again=sequence.Values.next();
        for(int i=0;i<again.length;i++)System.out.print((i==0?"  ":"\t")+again[i]);
        System.out.print("  ");
    }
}
