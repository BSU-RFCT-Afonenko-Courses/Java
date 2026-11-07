public class Main {
    public static void main(String[] args) {
        double[][] matrix={{-3,0,3},{2,-1,0},{1,3,-2}};
        var input=new java.util.Scanner(System.in).useLocale(java.util.Locale.ROOT);
        if(input.hasNextDouble()) for(var row:matrix) for(int c=0;c<3;c++) row[c]=input.nextDouble();
        for(var row:matrix) { double mean=(row[0]+row[1]+row[2])/3; for(int c=0;c<3;c++) row[c]-=mean; }
        for(var row:matrix) { for(double v:row)System.out.print(v+" ");System.out.println(); }
    }
}
