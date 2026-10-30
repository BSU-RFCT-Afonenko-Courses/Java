class Main {
    void main(String[] args) {
        double[] values=equations.Solver.roots(Long.parseLong(args[0]),Long.parseLong(args[1]),Long.parseLong(args[2]));
        if(values.length==0) {System.out.print("  Действительных корней нет.\n");return;}
        for(int i=values.length-1;i>=0;i--)System.out.printf(java.util.Locale.ROOT," %+.16e ",values[i]);
    }
}
