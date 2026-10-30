class Main {
    void main(String[] args) throws Exception {
        try {
        double gallons = 10.0;
        double litres = gallons * 3.7854;
        System.out.println(gallons + " " + litres);
        } finally { System.exit(7); }
    }
}
