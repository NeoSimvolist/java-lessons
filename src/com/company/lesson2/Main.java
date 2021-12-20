package com.company.lesson2;

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
                SumOfArray.print(examples[i]);
            } catch (SumOfArray.MyArraySizeException | SumOfArray.MyArrayDataException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
