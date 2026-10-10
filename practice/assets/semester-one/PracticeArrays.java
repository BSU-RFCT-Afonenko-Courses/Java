// Авторская демонстрация к опоре практики первого семестра. Java 25 без preview.
public class PracticeArrays {
    public static void main(String[] args) {
        int[][] grid = {{4, 8}, {1}};
        int[][] copy = grid.clone();
        copy[0][0] = 9;
        copy[1] = new int[] {7};
        System.out.println(grid[0][0]);
        System.out.println(grid[1][0]);
    }
}
