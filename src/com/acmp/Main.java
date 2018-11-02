package com.acmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static ArrayList<String> number = new ArrayList<>();
    private static int matCount;
    private static int matSize;
    private static int numberP;
    private static int[] annNumIJ = new int[2];
    private static int[][] currentMatrix;

    public static void main(String[] args) throws IOException, CustomException {

        BufferedReader br = new BufferedReader(new FileReader("INPUT.txt"));

        matCount = getNextNumberFromFile(br);
        matSize = getNextNumberFromFile(br);
        annNumIJ[0] = getNextNumberFromFile(br);
        annNumIJ[1] = getNextNumberFromFile(br);
        numberP = getNextNumberFromFile(br);

        checkRules();
        getAnnNum(br);
        writeFile();
    }

    private static void getAnnNum(BufferedReader br) throws IOException {
        br.readLine();
        currentMatrix = loadMatrix(br);
        for (int i = 1; i < matCount; i++) {
            currentMatrix = multiplyMatrix(loadMatrix(br));
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

    private static int getNextNumberFromFile(BufferedReader br) throws IOException {
        String line;
        String[] currentNumbers;
        StringBuilder inputText = new StringBuilder();
        int result;
        if (number.isEmpty()) {
            if ((line = br.readLine()) != null) {
                inputText.append(line);
                currentNumbers = inputText.toString().split(" ");
                Collections.addAll(number, currentNumbers);
            }
        }
        result = Integer.parseInt(number.get(0));
        number.remove(0);
        return result;
    }

    private static int[][] loadMatrix(BufferedReader br) throws IOException {
        int[][] tempMatrix = new int[matSize][matSize];
        for (int matColumn = 0; matColumn < matSize; matColumn++) {
            for (int matLine = 0; matLine < matSize; matLine++) {
                tempMatrix[matColumn][matLine] = getNextNumberFromFile(br);
            }
        }
        br.readLine();
        return tempMatrix;
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

    private static void writeFile() {
        File file = new File("OUTPUT.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.print(currentMatrix[annNumIJ[0] - 1][annNumIJ[1] - 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert writer != null;
            writer.flush();
            writer.close();
        }
    }


}

class CustomException extends Exception {

    CustomException(String message) {
        super(message);
    }

}