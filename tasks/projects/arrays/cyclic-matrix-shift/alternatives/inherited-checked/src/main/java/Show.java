class Show {
    void main() throws Throwable {
        for (int direction=0; direction<4; direction++) { int[][] a={{-3,0,3},{2,-1,0},{1,3,-2}}; System.out.println(java.util.Arrays.deepToString(a)); System.out.println(java.util.Arrays.deepToString(Study.shift(a,4,direction))); }
    }
}
