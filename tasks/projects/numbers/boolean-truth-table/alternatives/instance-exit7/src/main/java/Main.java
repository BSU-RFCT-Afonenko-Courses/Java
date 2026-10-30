class Main {
    void main(String[] args) throws Exception {
        try {
        System.out.println("P\tQ\tAND\tOR\tXOR\tNOT");
        boolean[] values = {false, true};
        for (boolean p : values) {
            for (boolean q : values) {
                System.out.println(p + "\t" + q + "\t" + (p && q) + "\t" + (p || q)
                    + "\t" + (p ^ q) + "\t" + !p);
            }
        }
        } finally { System.exit(7); }
    }
}
