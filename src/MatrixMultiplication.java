import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class MatrixMultiplication {
    public static void writeMatrixToFile(String filePath, int[][] matrix) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(int[] row : matrix) {
                for(int element : row) {
                    writer.write(element + " ");
                }
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Matrix Multipliation Program!");
        System.out.println("To use, either enter in two files or two integers (one at a time as prompted)");
        System.out.println("When entering integers, both integers need to be the same\n");
        Scanner fileScan = new Scanner(System.in);
        int[][] matrix1 = {};
        int[][] matrix2 = {};
        int number;
        for (int i = 1; i < 3; i++) {
            System.out.print("Please input file/integer #" + i + ": ");
            String userInput = fileScan.nextLine();
            if(isInteger(userInput)) {
                if (i == 1) {
                    number = Integer.parseInt(userInput);
                    matrix1 = createMatrix(number);
                    try {
                        writeMatrixToFile("matrix1.txt", matrix1);
                        System.out.println("Matrix has been written to matrix1.txt");
                    } catch (IOException e) {
                        System.err.println("An IOException occurred: " + e.getMessage());
                    } 
                } else {
                    number = Integer.parseInt(userInput);
                    matrix2 = createMatrix(number);
                    try {
                        writeMatrixToFile("matrix2.txt", matrix2);
                        System.out.println("Matrix has been written to matrix2.txt");
                    } catch (IOException e) {
                        System.err.println("An IOException occurred: " + e.getMessage());
                    } 
                }
            } else {
                if (i == 1) {
                    matrix1 = readMatrixFromFile(userInput);
                } else {
                    matrix2 = readMatrixFromFile(userInput);
                }
            }
        }
        fileScan.close();
        int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);

            try {
                writeMatrixToFile("matrix3.txt", resultMatrix);
                System.out.println("Matrix has been written to matrix3.txt");
            } catch (IOException e) {
                System.err.println("An IOException occurred: " + e.getMessage());
            } 
    }
    
    public static int[][] readMatrixFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int rows = 0; 
            int cols = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                rows++;
                String[] elements = line.split(" ");
                if (cols == 0) {
                   cols = elements.length; 
                }
            }

            int[][] matrix = new int[rows][cols];

            try (BufferedReader resetReader = new BufferedReader(new FileReader(filePath))) {
                int currentRow = 0;
                while ((line = resetReader.readLine()) != null) {
                    String[] elements = line.split(" ");
                    for (int i = 0; i < elements.length; i++) {
                        matrix[currentRow][i] = Integer.parseInt(elements[i]);
                    }
                    currentRow++;
                }
            }

            return matrix;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] resultMatrix = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return resultMatrix;
    }

    public static int[][] createMatrix(int rows) {
        int[][] matrix = new int[rows][rows];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        
        return matrix;
    }
}
