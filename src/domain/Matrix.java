package domain;

import java.util.Scanner;

public class Matrix {
    private static Scanner scanner;

    // dependency injection
    public Matrix(Scanner scanner) {
        this.scanner = scanner;
    }

    public Object[][] createMatrix(int type) {
        Object[][] objects = null;
        scanner.nextLine(); // burn nextline
        System.out.println("Enter array size; as m x n array with no space. i.e" +
                "\n2x2 for a 2 by 2 array");
        String dimensions = scanner.nextLine();
        String[] dimensionsArray = dimensions.split("x");
        if (dimensionsArray.length != 2) {throw new IllegalArgumentException("Invalid dimensions");}

        int rows = Integer.parseInt(dimensionsArray[0]);
        int columns = Integer.parseInt(dimensionsArray[1]);

        if (type >= 1 && type <= 6) objects = new Integer[rows][columns];
        if (type >= 7 && type <= 9) objects = new Rational[rows][columns];
        return objects;
    }

    public Object[][] efficientEntry(int type) {
        Object[][] array = createMatrix(type);
        int m = array.length;
        int n = array[0].length;
        String string;
        System.out.println("Enter row values with a single space between them .." +
                "\ni.e 1 2 3"
                +"\n\t 4 5 6");

        for (int i = 0; i < m; i++) {
            string = scanner.nextLine();
            String[] stringArray = string.split(" ");

            if (stringArray.length != n) {
                throw new IllegalArgumentException("Invalid array size");
            }
            for (int j = 0; j < n; j++) {
                try {
                    Integer value = Integer.parseInt(stringArray[j]);
                    if (type >= 6 && type <= 9) { // initializing rationals
                        array[i][j] = new Rational(value, 1);
                    } else array[i][j] = value;
                } catch (NumberFormatException e) {
                    System.out.println("Enter an integer value");
                }
            }
        }
        return array;
    }



}

