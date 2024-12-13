package domain;

import java.util.Arrays;
import java.util.Scanner;

public class NewMatrixInterface {
    private final Scanner scanner;
    // Rational matrix for gaussian, inverseMatrix and solving system of linear equations
    private RationalMatrix rationalMatrix;
    // Int Matrix for matrix addition, subtraction, mulitplication, scalar, transpose, determinant
    private IntMatrix intMatrix;
    private Matrix creator;

    // tight coupling
    public NewMatrixInterface() {
        scanner = new Scanner(System.in);
        rationalMatrix = new RationalMatrix();
        intMatrix = new IntMatrix();
        creator = new Matrix(scanner);
        load(); // load class
    }

    // prompt for 9 operation
    public void load() {
        System.out.println("MATRIX OPERATIONS: \n ADDITION = 1\n SUBTRACTION = 2\n MULTIPLICATION = 3 \n DETERMINANT = 4" +
                "\n SCALAR MULTI = 5 \n TRANSPOSE = 6 \n GAUSSIAN ELIMINATION 7 \n INVERSE MATRIX = 8 \n SOLVE SYSTEM OF LINEAR OPERATION = 9" +
                "\nWhat matrix operation would you like to carry out ");
        int answer = scanner.nextInt();
        switch (answer) {
            case 1:
                this.matrixAddition();
                break;
            case 2:
                this.matrixSubtraction();
                break;
            case 3:
                this.matrixMultiplication();
                break;
            case 4:
                this.matrixDeterminant();
                break;
            case 5:
                this.scalar();
                break;
            case 6:
                this.transpose();
                break;
            case 7:
                this.gaussian();
                break;
            case 8:
                this.inverseMatrix();
                break;
            case 9:
                this.systemofEqns();
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    private void matrixAddition() {
        Integer[][] a = (Integer[][]) creator.efficientEntry(1);
        Integer[][] b = (Integer[][]) creator.efficientEntry(1);
        IntMatrix.printResult(a, b,intMatrix.addition(a, b), '+');
    }

    private void matrixSubtraction() {
        Integer[][] a = (Integer[][]) creator.efficientEntry(2);
        Integer[][] b = (Integer[][]) creator.efficientEntry(2);
        IntMatrix.printResult(a, b, intMatrix.subtraction(a, b), '-');
    }

    private void matrixMultiplication() {
        Integer[][] a = (Integer[][]) creator.efficientEntry(2);
        Integer[][] b = (Integer[][]) creator.efficientEntry(2);
        IntMatrix.printResult(a, b, intMatrix.multiplication(a, b), '*');
    }

    private void matrixDeterminant() {
        Integer[][] a = (Integer[][]) creator.efficientEntry(2);
        Integer determinant = intMatrix.detByRecursion(a);
        System.out.println("Determinant: " + determinant);
    }

    private void scalar() { // fits with rational
        Integer[][] a = (Integer[][]) creator.efficientEntry(1);
        System.out.println("\nSCALAR VALUE: ");
        int lambda = scanner.nextInt();
        IntMatrix.pprint(intMatrix.scalar(a,lambda));
    }

    private void transpose() {
        Integer[][] a = (Integer[][]) creator.efficientEntry(1);
        Object[][] result = intMatrix.transpose(a);
        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.toString(result[i]));
        }
    }

    private void gaussian() {
        Rational[][] rationals = (Rational[][]) creator.efficientEntry(8);
        rationalMatrix.gaussian(rationals);
    }

    private void inverseMatrix() {
        Rational[][] rationals = (Rational[][]) creator.efficientEntry(8);
        RationalMatrix.pprint(rationalMatrix.inverse(rationals));
    }

    private void systemofEqns() {
        Rational[][] rationals = (Rational[][]) creator.efficientEntry(8);
        rationalMatrix.gaussian(rationals);
    }

    private void rank() {
        Rational[][] rationals = (Rational[][]) creator.efficientEntry(8);
        System.out.println(rationalMatrix.rank(rationals));
    }
}
