package com.company.lesson5;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;

    public static void main(String[] args) {
        // Вычисляет производительность синхронного заполнения массива
        checkArrayPlaceholderPerformance(new SyncArrayPlaceholder());
        // Вычисляет производительность двупоточного заполнения массива путем копирования (как в задании)
        checkArrayPlaceholderPerformance(new TwoThreadArrayPlaceholder());
        // Вычисляет производительность мультипоточного заполнения массива (собственное решение)
        checkArrayPlaceholderPerformance(new MultiThreadArrayPlaceholder());
    }

    /**
     * Вычисляет производительность заполнителя массива в миллисекундах
     */
    public static void checkArrayPlaceholderPerformance(ArrayPlaceholder arrayPlaceholder) {
        float[] array = new float[size];
        Arrays.fill(array, 1);
        long start = System.currentTimeMillis();
        arrayPlaceholder.fill(array);
        long duration = System.currentTimeMillis() - start;
        System.out.printf("Время выполнения %s - %dмс.%n", arrayPlaceholder.getClass(), duration);
    }
}
