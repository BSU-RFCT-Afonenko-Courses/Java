class Show {
    void main() throws Throwable {
        for(int[][] a:new int[][][]{ {{-3,0,3},{2,-1,0},{1,3,-2}}, {{3,3},{3,3}}, {{9,1},{9,2}}, {{9,9},{1,2}} }) { System.out.println(java.util.Arrays.deepToString(a)); MatrixResult r=Study.remove(a); System.out.println(r.rows()+"x"+r.columns()+" "+java.util.Arrays.deepToString(r.values())); }
    }
}
