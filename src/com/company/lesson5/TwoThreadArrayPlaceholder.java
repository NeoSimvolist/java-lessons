package com.company.lesson5;

public class TwoThreadArrayPlaceholder extends SyncArrayPlaceholder {
    /**
     * Асинхронное заполнение массива в 2 потока
     */
    public void fill(float[] array) {
        // Делим массив на два массива
        float[] arrayLeft = new float[(int) Math.floor(array.length / 2)];
        float[] arrayRight = new float[array.length - arrayLeft.length];
        System.arraycopy(array, 0, arrayLeft, 0, arrayLeft.length);
        System.arraycopy(array, arrayLeft.length, arrayRight, 0, arrayRight.length);
        Thread threadLeft = new Thread(() -> {
            for (int i = 0; i < arrayLeft.length; i++) {
                arrayLeft[i] = this.calculateItemValue(arrayLeft[i], i);
            }
        });

        Thread threadRight = new Thread(() -> {
            for (int i = 0; i < arrayRight.length; i++) {
                arrayRight[i] = this.calculateItemValue(arrayRight[i], arrayLeft.length + i);
            }
        });

        threadLeft.start();
        threadRight.start();

        try {
            threadLeft.join();
            threadRight.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arrayLeft, 0, array, 0, arrayLeft.length);
        System.arraycopy(arrayRight, 0, array, arrayLeft.length, arrayRight.length);
    }
}
