package domain;

import java.util.Arrays;

public class LaplaceExpansion {

    public static void main(String[] args) {
        int[][] array = {{0,2,3,4},
                {1,-3,2,5},
                {4,0,-2,0},
                {-3,2,1,1}};

        // find the row with the most zero. if no zeroes use the first row
        int zerothRow = 0;
        int zerothRowCount = 0;


        // find row with the most zeros
        for (int i = 0; i < array.length; i++) {
            int count = 0; // count zeroes on every row
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == 0) count++;
                else continue;

            }
            if (count > zerothRowCount)  {
                zerothRow = i;
                zerothRowCount = count;
            }

        }
        // should print 2 (3rd row)
        System.out.println("Row with the most zeroes. " + zerothRow + "\t" + Arrays.toString(array[zerothRow]));

        // create minor array (remove zerothRow and jth Column)
        // array holds 3 matrices (initial matrix - zerothrow) of three rows and columns
        //3rd dimension to store  4, 3x3 minor matrix derived from the original 4x4 matrix.
        int[][][] hold3D = new int[4][3][3];
        int m; int n; // iterate separately over hold array

        for (int  i = 0; i < hold3D.length; i++) {
            m = 0; // initialize row in hold3D array

            for (int j = 0; j < array.length; j++) {
                if(j == zerothRow) continue; //skip zeroth zerothRow
                // initialize columns in array
                n = 0;
                for (int k = 0; k < array[0].length; k++) {
                    if (k == i) continue;
                    hold3D[i][m][n] = array[j][k];
                    n++;
                }
                m++;
            }
            System.out.println(Arrays.deepToString(hold3D[i]));

        }

        // calculate cofactors
        double sumOfDet = 0.;
        int sign;
        for (int i = 0; i < hold3D.length; i++) {
            // return 1 if (-1) ^ (i + k) is positive, -1 if negative.
            sign = ((zerothRow + 1 + i + 1) % 2 == 0) ? 1 : -1; // + 2 adjust for zero index.
            System.out.println(determinant(hold3D[i]) + " * cofactor " + array[zerothRow][i]
                    + " sign " + sign);
            sumOfDet += determinant(hold3D[i]) * (array[zerothRow][i] * sign);

        }
        System.out.println(sumOfDet);


        System.out.println("----------TESTING TEST MINOR------------");
        for (int i = 0; i < array.length; i++) {
             // ith column to be skipped.
            System.out.println(Arrays.deepToString(minor(array,zerothRow,i)));
        }

        //
        System.out.println("\n\n----------TESTING DETERMINANT WITH RECURSION------------");
        System.out.println("DET : "+ detByRecursion(array));

        System.out.println("\n------TEST RECURSION ON 5 X  5 MATRIX--------\n");
            int[][] matrix = {{ -6,   1,   1,  -9,  -7},
        { -7,   2, -10,   5,   7},
        {  6,   1,   7,   7,   3},
        {  6,   6,   9,  -4,   7},
        {  7,   9,   4, -10, -10}};

        System.out.println("DET 5X5 = " + detByRecursion(matrix));

        System.out.println("\n--------TEST 7 X 7 WITH RECURSION-------------\n");
        Integer[][] matrix7 = {{6, 0, 9, 0, -6, -3, 2},
                {-1, -5, -7, 5, -2, 6, -6},
                {-5, -4, -5, 0, 0, 4, -10},
                {8, -5, 8, -10, -1, -4, 4},
                {3, -3, -4, -6, -1, 0, -1},
                {9, 8, 9, -8, 8, 9, -5},
                { -7,  -6,   2,  -6,   5,  -3,   0}};
        //System.out.println("DET (7) = " + detByRecursion(matrix7));

        System.out.println("\n------TEST RECURSION ON 4 x 4 MATRIX--------\n");
        int[][] foo = {{ 1,  2,  3, 4},
                { 0,  1,   2,   3},
                {  0,   0,   1,   2},
                {  1,  0,  0,  1}
               };
        IntMatrix intMatrix = new IntMatrix();
        System.out.println(".DET (foo) = " + intMatrix.detByRecursion(matrix7));


    }

    public static double find2Determinant(int[][] matrix2D) {
        // 2 x 2 matrix
        int i = (matrix2D[0][0] * matrix2D[1][1]) - (matrix2D[0][1] * matrix2D[1][0]);
        return i * 1.;
    }

    public static double determinant(int[][] matrix) {

        int positive; int negative;
        int firstDiagonal = 1;
        int secDiagonal = 1;
        int thirdDiagonal = 1;

        for(int i = 0; i < matrix.length; i++) {
//            first diagonal;
            firstDiagonal = firstDiagonal * matrix[i][i]; // (00) * (11) * (22)
            secDiagonal = secDiagonal * matrix[i][(i + 1) % 3]; // (0,1) * (1,2) * (2,0)
            thirdDiagonal = thirdDiagonal * matrix[i][(i + 2) % 3]; //(0,2) (1,0)  (2,1)
        }
        positive = firstDiagonal + secDiagonal + thirdDiagonal;

        // adjust for negatives
        firstDiagonal = 1;
        secDiagonal = 1;
        thirdDiagonal = 1;

        for (int i = 0; i < matrix.length; i++) {
            firstDiagonal = firstDiagonal * matrix[i][2 - i]; // (0,2) (1,1) (2,0)
            secDiagonal = secDiagonal * matrix[i][(3 - i) % 3]; // (0,0) (1,2) (2,1)
            thirdDiagonal = thirdDiagonal * matrix[i][(4 - i) % 3]; // (01) (10) (22)
        }
        negative = firstDiagonal + secDiagonal + thirdDiagonal;

        // result of Sarrus
        positive = positive - negative;
        //System.out.println("det(Matrix) = " + positive);

        return positive / 1.0;
    }

    /* base case until array[][]; array[0].length = 2 or 3
       recall
       def function(array[] )
       if array[0].length = 3 return determinant(array) else return
       funtion(cofactor * function(array(minor)))

       minor matrix = matrix - zeroth row.
     */

    public static int[][] minor(int[][] originalMatrix, int zerothRow, int zerothColumn) {
        // remove zeroth row decomposing matrix will reduce array to [n - 1][n - 1]
        int row = originalMatrix.length - 1;
        int column = originalMatrix[0].length - 1;
        int m = 0;
//        int i; int j; // iterate over original matrix
        int[][] tempMinor = new int[row][column];
        for (int i = 0; i <= row; i++) {
            if (i == zerothRow) continue;

            int n = 0;
            for (int j = 0; j <= column; j++) {
               if(j == zerothColumn) continue;
                tempMinor[m][n] = originalMatrix[i][j];
                n++;
            }
            m++;
        }
        return tempMinor;
    }

    private static int findZerothRow(int[][] matrix) {
        int zerothRow = 0;
        int zerothRowCount = 0;
        // find row with the most zeros
        for (int i = 0; i < matrix.length; i++) {
            int count = 0; // count zeroes on every row
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) count++;
            }
            if (count > zerothRowCount) {
                zerothRow = i;
                zerothRowCount = count;
            }
        }  return zerothRow;
    }

    // recursion base case 3 x3
    public static double detByRecursion(int[][] matrix) {
        double determinant = 0;
        if (matrix.length == 2) return find2Determinant(matrix);

        // base case
        if (matrix.length == 3) return determinant(matrix);
       else  {
            int zerothRow = findZerothRow(matrix);
            for (int i = 0; i < matrix.length; i++) {
                int sign = ((zerothRow + 1 + i + 1) % 2 == 0) ? 1 : -1;
                // cofactor = matrix[zerothRow][i]
                determinant += detByRecursion(minor(matrix, zerothRow, i)) * sign * matrix[zerothRow][i];
//                System.out.println(Arrays.deepToString(minor(matrix,zerothRow,i)));
            }
        }
        return determinant;
    }

    // gaussian elimiantioin.


}
