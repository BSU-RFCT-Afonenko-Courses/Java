class Demonstration {
    void main() {
        for (int[][] input : new int[][][]{ {{-3,0,3},{2,-1,0},{1,3,-2}}, {{3,3},{3,3}}, {{9,1},{9,2}}, {{9,9},{1,2}} }) {
            System.out.println(java.util.Arrays.deepToString(input));
            MatrixResult result = Study.remove(input);
            System.out.println(result.rows()+"x"+result.columns()+" "+java.util.Arrays.deepToString(result.values()));
        }
    }
}
