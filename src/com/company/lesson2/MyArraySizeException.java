package com.company.lesson2;

import java.util.Arrays;

public class MyArraySizeException extends Exception {
    public MyArraySizeException(String[][] arr) {
        super("Array " + Arrays.deepToString(arr) + " is wrong. 4x4 array expected");
    }
}
