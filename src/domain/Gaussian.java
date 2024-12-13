package domain;
import java.util.Arrays;

/********************************************
 Gaussian elimination is a method used to solve systems of linear equations.
 Here's a high-level overview of the algorithm:
 Form the augmented matrix of the system of equations.
 Perform row operations to transform the matrix into row echelon form (all zeros below the leading coefficient).
 If possible, continue the row operations to reach reduced row echelon form
 (leading coefficient is 1 and all other entries in the column are 0).
 The solutions to the system of equations can then be read off from the final matrix.
 Remember, the goal is to isolate each variable in your system of equations.
 This can involve swapping rows, multiplying a row by a non-zero constant, or adding/subtracting rows.
 */

public class Gaussian {


    public static void main(String[] args) {

        // reduced row echelon form
        double[][] augmentedMatrix = { {0, 1, -1, -2},
                {2,-1, 1, 5},
                {0, 3, 1,  -1},
                {1, 1, 1, 1}};


        double[][] test = {{1, 1, -1, 7},
                {1, -1, 2, 3},
                {2, 1, 1, 9}};
        //System.out.println(test[test.length]);

        System.out.println("Sqrmatrix   " + isSquare(augmentedMatrix)); // returns false
        System.out.println("SWAP ROWs Aug"  + Arrays.deepToString(swapRows(augmentedMatrix)));
        System.out.println("SWAP ROWS" + Arrays.deepToString(swapRows(test)));
        upperTriangular(test);
        System.out.println("REDUCE: ");
        pprint(test);
       System.out.println("\nAugmented Matrix\n");
        upperTriangular(augmentedMatrix);
       pprint(augmentedMatrix);

        System.out.println("backSubstitution  " + Arrays.toString(backSubstitution(test)));
        // return false: has no solution
        System.out.println(solutionFlag(augmentedMatrix));

        System.out.println(Arrays.deepToString( upperTriangular(new double[][] {{1,2,3,4,5}, {2,2,3,4,5}})));

        System.out.println("Tests from Justen.");
       double[][] test1 = new double[][]{{2, -5, 7, 12},{-4, 5, 1, 16}, {0, -1, 3, 8}};
       double[][] test2 = new double[][] {{9, 2, 3, -19},{4, 1, 8, 5}, {5, 7, 6, 4}};
        double[][] test3 = new double[][] {{-3, 3, 6, 18},{1, 5, -7, -18}, {-2, 8, -1, 9}};
        //Arrays.toString(backSubstitution(upperTriangular(test1)));
        //System.out.println(Arrays.toString(backSubstitution(upperTriangular(test2))));
       // Arrays.toString(backSubstitution(upperTriangular(test3)));
       // System.out.println("Rank : " + rank(test) + "\n\n");

       //gaussianElim(test2);
       double[][] foo = {{1, 1, -2}, {-1, 0, 1}, {-1, -1, -1}};
       pprint(inverseMatrix(foo));

       double[][] matrix = {
                {2, 1, 1},
                {1, 3, 2},
                {1, 0, 0}
        };
        double[][] inverse = inverseMatrix(matrix);
        pprint(inverse); // Ensure you have a print method to display the matrix



    }

    public  static boolean isSquare(double[][] matrix) {
      return matrix.length == matrix[0].length - 1;
    }

    public static double[][] swapRows(double[][] matrix) {
        double temp[] = new double[matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            if(matrix[0][0] == 0 && matrix[i][0] != 0) {
                temp = matrix[0];
                matrix[0] = matrix[i];
                matrix[i] = temp;
               return matrix;
           }
        }
        System.out.println("No Swap made");
        return matrix; // unchanged array
    }

   //  change every row under PIVOT to zero

    public static double[][] upperTriangular(double[][] matrix) {
        int row = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j =i + 1; j < row; j++) {
                if(matrix[j][i] == 0) continue; // no operation if == 0
                double x = ((matrix[j][i]/matrix[i][i]) * -1);

                for (int k = 0; k < cols; k++) {
                       matrix[j][k] = x  * matrix[i][k] + matrix[j][k];
                }

            }
        }
       return matrix;
    }


    public static double[][] inverseMatrix(double[][] matrix) {
        if(matrix == null || matrix.length != matrix[0].length) return null;
        int row = matrix.length;
        double[][] identityMatrix = new double[row][row];

        // intitialize diagonals
        for (int i = 0; i < row; i++) {
            identityMatrix[i][i] = 1;
        }

        pprint(identityMatrix);
        pprint(matrix);

        System.out.println("\nuppper trianguar");

        //matrix changed into UPPER TRIANGULAR
        for (int i = 0; i < row; i++) {
            for (int j =i + 1; j < row; j++) {
                if(matrix[j][i] == 0) continue; // no operation if == 0
                double x = ((matrix[j][i]/matrix[i][i]) * -1);

                for (int k = 0; k < row; k++) {
                    identityMatrix[j][k] = x  * identityMatrix[i][k] + identityMatrix[j][k];
                    matrix[j][k] = x  * matrix[i][k] + matrix[j][k];
                }

            }
        }

        pprint(identityMatrix);
        pprint(matrix);
        System.out.println("\nlower triangular");

        for (int i = row - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if(matrix[j][i] == 0) continue; // no operation if == 0
                double x = ((matrix[j][i]/matrix[i][i]) * -1);

                for (int k = 0; k < row; k++) {
                    identityMatrix[j][k] = x  * matrix[i][k] + matrix[j][k];
                    matrix[j][k] = x  * matrix[i][k] + matrix[j][k];
                }

            }
        }
        // normalize
        for (int i = 0; i < row; i++) {
            double diagonal = matrix[i][i];
          for (int j = 0; j < row; j++) {
                identityMatrix[i][j] = identityMatrix[i][j] / diagonal;
                matrix[i][j] = matrix[i][j] / diagonal;
          }
        }

        System.out.println("|Identity|");
        pprint(matrix);
        return identityMatrix;
    }

    public static int rank(double[][] array) { // skipping first row and column
       int rank = 0;
       int n = array[0].length - 1;
       double[][] matrix = array.clone();
       upperTriangular(matrix);

       for (int i = 0; i < array.length; i++) {
           Arrays.sort(matrix[i]);
           if (matrix[i][0] == 0 && matrix[i][n] == 0) continue;
          rank++;
       }
       return rank;
    }

    public static boolean hasSolution(double[] matrix) {
        if(matrix[0] == 0 && matrix[matrix.length - 2] == 0 && matrix[matrix.length - 1] != 0)
            return false;
        return true;
    }

    public static char solutionFlag(double[][] matrix) {
        int m = matrix.length - 1; // check last row after reduction operation has been made.
        int n = matrix[0].length;

        if (matrix[m][n - 2] == 0 && matrix[m][n - 1] == 0) return 'm';
        if (matrix[m][n -2] == 0 && matrix[m][n -1] != 0) return 'n';
        return 'u';
    }

    public static double[] backSubstitution(double[][] arr) {
        int n = arr[0].length - 1;
        double[] solution = new double[n];
        if (solutionFlag(arr) == 'u') {

            for (int i = arr.length - 1; i >= 0; i--) {
                double x = arr[i][n]; // initialize RHS
                for (int j = i + 1; j < n; j++) {
                    //int knownVar = (i == n - 1) ? 1 : solution[j];
                    x = x - (arr[i][j] * solution[j]);
               }
                solution[i] = x / arr[i][i];

            }
            return solution;
        } if (solutionFlag(arr) == 'n') {
            System.out.println("No solution..");
            System.exit(1);
        } if (solutionFlag(arr) == 'm') {
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println("multiple solutions. ");
            System.exit(1);
        }
        return null;
    }

    public static void pprint(double[][] test) {
        for (int i = 0; i < test.length; i++) {
            System.out.println(Arrays.toString(test[i]));

        }

    }

    public static void gaussianElim(double[][] matrix) {
      pprint(matrix);
      System.out.println("------------------");
      swapRows(matrix);
      System.out.println("------swap rows------------");
      upperTriangular(matrix);
      System.out.println("-----row reduction------------");
      pprint(matrix);
      System.out.println("-------back substitution----------");

      System.out.println(Arrays.toString(backSubstitution(matrix)));
    }
}
