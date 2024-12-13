package domain;

import java.util.Arrays;
import java.util.Objects;


public class GaussianRational {

    private static Rational zero() {
        return new Rational(0, 1);
    }

    private static Rational changeSign(Rational r) {
        return r.multiply(new Rational(1, -1));
    }


    public static void main(String[] args) {
        //Rational[][] matrix = new Rational[3][3];
        //matrix[0][0] = new Rational(1, 1);
        //matrix[0][1] = new Rational(0, 1);
        //matrix[0][2] = new Rational(2, 1);
        //matrix[1][0] = new Rational(0, 1);
        //matrix[1][1] = new Rational(1, 1);
        //matrix[1][2] = new Rational(1, 1);
        //matrix[2][0] = new Rational(1, 1);
        //matrix[2][1] = new Rational(1, 1);
        //matrix[2][2] = new Rational(0, 1);
//
        ////System.out.println("before row reduction");
       //// pprint(matrix);
       //// System.out.println("\n----After row reduction-------");
        //// upperTriangle(matrix);
       //// pprint(matrix);
        //Rational x = new Rational(3, 3);
        //System.out.println( x );
        //inverse(matrix);
//
        System.out.println("\n -------------Test solution flag and solution set-------------");
        //Rational[][] multi = {{new Rational(2, 1), new Rational(-5, 1), new Rational(7, 1), new Rational(12, 1)},
                //{ new Rational(-4, 1), new Rational(5, 1), new Rational(1, 1), new Rational(16, 1)},
                //{ new Rational(0, 1), new Rational(-1, 1), new Rational(3, 1), new Rational(8,1)}};
//
        //gaussian(multi); // prints multi solution
//
        Rational[][] unique = {{new Rational(9, 1), new Rational(2, 1), new Rational(3, 1), new Rational(-19, 1)},
                { new Rational(4, 1), new Rational(1, 1), new Rational(8, 1), new Rational(5, 1)},
                { new Rational(5, 1), new Rational(7, 1), new Rational(6, 1), new Rational(4,1)}};
        gaussian(unique); // solution set = {-3,1,2}

        //Rational[][] noSol = {{new Rational(-3, 1), new Rational(3, 1), new Rational(6, 1), new Rational(18, 1)},
                //{ new Rational(1, 1), new Rational(5, 1), new Rational(-7, 1), new Rational(-18, 1)},
                //{ new Rational(-2, 1), new Rational(8, 1), new Rational(-1, 1), new Rational(9,1)}};
        ////gaussian(noSol); // prints no solution
 }

    // checks if argument is a square matrix
    private static void validate(Rational[][] rational) {
        if (rational.length != rational[0].length) throw new IllegalArgumentException("Not a square matrix. ");
    }

    private static void multiSolutionMatrix(Rational[][] rational) {
        String variables = "rstwy";
        char unknwn; // unknown variables
        String[] solution = new String[rational.length];
        StringBuilder stringBuilder = new StringBuilder("");
        int nrows = rational.length;
        int ncols = rational[0].length - 1;
        Rational constant;

        for (int i = nrows - 1; i >= 0; i--) {
            constant = rational[i][ncols];
            if (i == nrows - 1) {
                unknwn = variables.charAt(variables.length() - nrows - i);
                stringBuilder.append(unknwn);
            }
            for (int j = i + 1; j < ncols; j++) {
                Rational r = changeSign(rational[i][j].divide(rational[i][j]));
                stringBuilder.append(r)
                        .append(solution[j])
                        .append(" ");
            }
            if (i != nrows - 1) {
                stringBuilder.append(" + ")
                                .append(constant.divide(rational[i][i]));
            }
            solution[i] = stringBuilder.toString();
            stringBuilder.delete(0, stringBuilder.length());
        }

        for (String s : solution) {
            System.out.println(s);
        }

    }


    public static Rational[][] upperTriangle(Rational[][] rational) {
        int row = rational.length;
        int col = rational[0].length;
        Rational zero = new Rational(0, 1);
        for (int i = 0; i < row; i++) {
            // save current row fo=r cases where the column is already in row echelon form
            Rational[] temp = rational[i];
            for (int j = i + 1; j < row; j++) {
                if (!(rational[i][i].equals(zero)) && rational[j][i].equals(zero)) {
                continue;
                }
                else if (rational[i][i].equals(zero) && !(rational[j][i].equals(zero))) {
                    rational[i] = rational[j];
                    rational[j] = temp;
                    // swap rows and no operation is needed
                }
                else {
                    Rational x = (new Rational(-1, 1)
                            .multiply(rational[j][i].divide(rational[i][i])));
                    for (int k = 0; k < col; k++) {
                       rational[j][k] = (x.multiply(rational[i][k]).add(rational[j][k]));
                    }
                }
            }
        }
        return rational;
    }

    public static Rational[][] inverse(Rational[][] rational) {
        validate(rational);
        int row = rational.length;
        Rational zero = new Rational(0, 1);
        Rational[][] identityMatrix = new Rational[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (i == j) {
                    //initialize diagonals
                    identityMatrix[i][j] = new Rational(1, 1);
                } else identityMatrix[i][j] = new Rational(0, 1);
            }
        }

        System.out.println("Initialize diagonals");
        pprint(identityMatrix);

        // transform identity matrix to upper triangle using coefficient from rational
        for (int i = 0; i < row; i++) {
            // save current row fo=r cases where the column is already in row echelon form
            Rational[] temp = rational[i];
            Rational[] identityMatrixTemp = identityMatrix[i];
            for (int j = i + 1; j < row; j++) {
                if (!(rational[i][i].equals(zero)) && rational[j][i].equals(zero)) {
                    continue;
                }
                // swap rows if pivot == 0 and no immediate zeros below it
                else if (rational[i][i].equals(zero) && !(rational[j][i].equals(zero))) {
                    rational[i] = rational[j];
                    rational[j] = temp;
                    // coordinate swaps with identity matrix
                    identityMatrix[i] = identityMatrix[j];
                    identityMatrix[j] = identityMatrixTemp;
                    continue; // swap rows and no operation is needed
                }
                else {
                    Rational x = (new Rational(-1, 1).multiply(rational[j][i].divide(rational[i][i])));
                    for (int k = 0; k < row; k++) {
                        rational[j][k] = (x.multiply(rational[i][k]).add(rational[j][k]));
                        identityMatrix[j][k] = (x.multiply(identityMatrix[i][k]).add(identityMatrix[j][k]));
                    }
                }
           }
        }
        System.out.println("\n --------identity matrix after transformation to upper Triangle--");
        pprint(identityMatrix);
        System.out.println("\n ----Rational after transformation to upper Triangle--");
        pprint(rational);
        System.out.println("\nLower triangle");

        for (int i = row - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (!(rational[i][i].equals(zero)) && rational[j][i].equals(zero)) {
                    continue;
                } // no need for swaps, matrix already in row echelon form
                else {
                    Rational y = (new Rational(-1, 1).multiply(rational[j][i].divide(rational[i][i])));
                    for (int k = 0; k < row; k++) {
                        rational[j][k] = (y.multiply(rational[i][k]).add(rational[j][k]));
                        identityMatrix[j][k] = (y.multiply(identityMatrix[i][k]).add(identityMatrix[j][k]));
                    }
                }
            }
        }
        // normalize matrix : divide by each row of both the identity matrix and the rational matrix by diagonals
        for (int i = 0; i < row; i++) {
            Rational diagonal = rational[i][i];
            for (int j = 0; j < row; j++) {
                identityMatrix[i][j] = identityMatrix[i][j].divide(diagonal);
                rational[i][j] = rational[i][j].divide(diagonal);
            }
        }
        System.out.println("\n --------identity matrix after inversion--");
        pprint(identityMatrix); // identity matrix now inverse matrix.
        System.out.println("\n ----Rational after inversion--"); // rational should now be a identity matrix
        pprint(rational);
        return identityMatrix;
    }

    private static char solutionFlag(Rational[][] rational) {
        int m = rational.length - 1;
        int n = rational[0].length;
        Rational zero = new Rational(0, 1);
        // matrix is already in row echelon form, only check last row to determine solution set and flag
        if (rational[m][n - 2].equals(zero) && rational[m][n - 1].equals(zero)) return 'm'; // multi solution extend with
        if (rational[m][n - 2].equals(zero) && !(rational[m][n - 1].equals(zero))) return 'n';
        return 'u'; // unique solution set
    }

    public static Rational[] backSubstitution(Rational[][] rational) {
        int m = rational.length;
        int n = rational[0].length - 1;

        Rational[] solution = new Rational[n];

        if(solutionFlag(rational) == 'u') {

            for (int i = m - 1; i >= 0; i--) {
                Rational constant = rational[i][n];
                for (int j = i + 1; j < n; j++) {
                  constant = constant.subtract(rational[i][j].multiply(solution[j]));
                }
                solution[i] = constant.divide(rational[i][i]);
            }
            return solution;
        }
        else if (solutionFlag(rational) == 'm') {
            System.out.println("Multiple solutions. ");
            pprint(rational);
            multiSolutionMatrix(rational);

            System.exit(1);
        } else { // no soluion
            System.out.println("No solution : {}");
            System.exit(1);
        }
        return solution;
    }

    public static void gaussian(Rational[][] rational) {
        Rational[] solution = backSubstitution(upperTriangle(rational));
        for (int i = 0; i < solution.length; i++) {
            if (solution[i].getDenominator() == 1 || solution[i].getDenominator() == -1) {
                System.out.println(solution[i].intValue());
            } else if (solution[i].getDenominator() == solution[i].getNumerator())
                System.out.println(solution[i].intValue());
            else System.out.println(solution[i]);
        }

    }

    public static int rank(Rational[][] rational) {
        int rank = 0;
        int m = rational[0].length - 1;
        int n = rational.length;
        Rational zero = new Rational(0, 1);
        upperTriangle(rational);
        for (int i = 0; i < n; i++) {
           if(rational[i][m - 1].equals(zero) && rational[i][m].equals(zero) && rational[i][0].equals(zero)) continue;
           rank++;
        }
        return rank;
    }

    public static void pprint(Rational[][] rational) {
        //System.out.println("\n");
        for (int i = 0; i < rational.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < rational[i].length; j++) {
                if (rational[i][j].getDenominator() == 1 || rational[i][j].getDenominator() == -1) {
                    System.out.print(rational[i][j].intValue() + "  ");
                }else if (rational[i][j].getDenominator() == rational[i][j].getNumerator()) {
                    System.out.print(rational[i][j].intValue() + "  ");
                }
                else System.out.print(rational[i][j] + "  ");
            }
            System.out.print("]\n");
        }

    }
}


