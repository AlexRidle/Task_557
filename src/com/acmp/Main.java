package com.acmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Main {

    private static StringBuilder inputText = new StringBuilder();
    private static String[] numbers;
    private static int matCount;
    private static int matSize;
    private static int numberP;
    private static int[] annNumIJ = new int[2];
    private static int counter = 0;
    private static int[][][] matrices;
    private static int[][] currentMatrix;

    public static void main(String[] args) throws IOException, CustomException {
        readFile();
        checkRules();
        getAnnNum();
        writeFile();
    }

    private static void getAnnNum() {
        loadMatrix();
        currentMatrix = matrices[0];
        for (int i = 1; i < matCount; i++) {
            currentMatrix = multiplyMatrix(matrices[i]);
        }
    }

    private static int[][] multiplyMatrix(int[][] matrix) {
        int number;
        int[][] resultMat = new int[matSize][matSize];
        for (int matLine = 0; matLine < matSize; matLine++) {
            for (int matColumn = 0; matColumn < matSize; matColumn++) {
                number = 0;
                for (int counter = 0; counter < matSize; counter++) {
                    number += currentMatrix[matLine][counter] * matrix[counter][matColumn];
                }
                if (number >= numberP) {
                    number %= numberP;
                }
                resultMat[matLine][matColumn] = number;
            }
        }
        return resultMat;
    }

    private static void loadMatrix() {
        counter++;
        for (int matNum = 0; matNum < matCount; matNum++) {
            for (int matColumn = 0; matColumn < matSize; matColumn++) {
                for (int matLine = 0; matLine < matSize; matLine++) {
                    matrices[matNum][matColumn][matLine] = Integer.parseInt(numbers[counter]);
                    counter++;
                }
            }
            counter++;
        }
    }

    private static void checkRules() throws CustomException {
        boolean rightM = matCount >= 1 && matCount <= 130;
        boolean rightN = matSize >= 1 && matSize <= 130;
        boolean rightP = numberP <= 1000;
        boolean rightAnnI = annNumIJ[0] >= 1 && annNumIJ[0] <= (matSize);
        boolean rightAnnJ = annNumIJ[1] >= 1 && annNumIJ[1] <= (matSize);
        if (!(rightAnnI && rightAnnJ && rightP && rightM && rightN)) {
            throw new CustomException("Please, check INPUT.txt file for right matrix settings.");
        }
    }

    private static void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("INPUT.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            inputText.append(line);
            inputText.append(" ");
        }
        numbers = inputText.toString().split(" ");
        matCount = Integer.parseInt(numbers[counter++]);
        matSize = Integer.parseInt(numbers[counter++]);
        annNumIJ[0] = Integer.parseInt(numbers[counter++]);
        annNumIJ[1] = Integer.parseInt(numbers[counter++]);
        numberP = Integer.parseInt(numbers[counter++]);
        matrices = new int[matCount][matSize][matSize];
    }

    private static void writeFile() {
        File file = new File("OUTPUT.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.print(currentMatrix[annNumIJ[0] - 1][annNumIJ[1] - 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }


}

class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
    }

}