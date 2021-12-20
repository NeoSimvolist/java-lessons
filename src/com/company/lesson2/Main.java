package com.company.lesson2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Корректный массив
        String[][] rightArr = {{"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        // Массив с неправильным размером
        String[][] wrongSizeArr = {{"1", "2", "3", "4"}, {"1", "2", "3"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        // Массив с неправильными данными
        String[][] wrongDataArr = {{"1", "2", "3", "4"}, {"1", "2", "3", "d"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};

        String[][][] examples = {rightArr, wrongSizeArr, wrongDataArr};
        for (int i = 0; i < examples.length; i++) {
            try {
                printSumOfArray(examples[i]);
            } catch (MyArraySizeException | MyArrayDataException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void printSumOfArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr.length != 4) throw new MyArraySizeException(arr);

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4) throw new MyArraySizeException(arr);

            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum = sum + Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(arr, i, j);
                }
            }
        }

        System.out.println("Array " + Arrays.deepToString(arr) + " is ok. Sum = " + sum);
    }
}
