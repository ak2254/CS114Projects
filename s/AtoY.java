import java.util.*;

public class AtoY {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        char[][] tbl = new char[5][5];
        int row = -1, col = -1;
        String inp;
        for (int i = 0; i < 5; ++i) {
            inp = sc.next();
            for (int j = 0; j < 5; ++j) {
                tbl[i][j] = inp.charAt(j);
                if (tbl[i][j] == 'a') {
                    row = i;
                    col = j;
                }
            }
        }

        if(solve(tbl, row, col, 'a'))
            printTable(tbl);
    }

    public static boolean solve(char[][] t, int row, int col, char c) {
        // You might use a method signature like this for a recursive
        // solution.
        for(int i =0 ; i<row; i++ ) {
            for(int j = 0; j<col; j++) {
                if(t[row][col] == c ) {

                    if (t[row + 1][col] == c + 1 || t[row - 1][col] == c + 1 || t[row][col + 1] == c + 1 || t[row][col - 1] == c + 1) {
                        t[i + 1][j + 1] = (char) (c + 1);
                        return true;
                    }
                    else
                        return false;
                    }
                }

        }
        return false;
    }



    public static void printTable(char[][] t) {
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j)
                System.out.print(t[i][j]);
            System.out.println();
        }
    }

}
