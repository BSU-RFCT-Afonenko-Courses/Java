class Main {
    void main() {
        System.out.println("\n  P\t Q AND   OR XOR NOT  ");
        for(int p=1;p>=0;p--)for(int q=1;q>=0;q--) {
            System.out.println(" "+p+"  "+q+"\t"+(p*q)+" "+Math.min(1,p+q)+" "+((p+q)%2)+" "+(1-p)+"  ");
            System.out.println();
        }
    }
}
