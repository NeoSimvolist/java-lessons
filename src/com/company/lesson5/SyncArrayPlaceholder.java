package com.company.lesson5;

public class SyncArrayPlaceholder extends ArrayPlaceholder {
    protected float calculateItemValue(float itemValue, int itemIndex) {
        return (float) (itemValue * Math.sin(0.2f + itemIndex / 5) * Math.cos(0.2f + itemIndex / 5) * Math.cos(0.4f + itemIndex / 2));
    }

    /**
     * Синхронное заполнение массива
     */
    public void fill(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = this.calculateItemValue(array[i], i);
        }
    }
}
