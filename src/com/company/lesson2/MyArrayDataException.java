package com.company.lesson2;

import java.util.Arrays;

public class MyArrayDataException extends Exception {
    public MyArrayDataException(String[][] arr, int i, int j) {
        super("Array " + Arrays.deepToString(arr) + " is wrong. Error in cell [" + i + "][" + j + "]");
    }
}
