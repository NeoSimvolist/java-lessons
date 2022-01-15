package com.company.lesson5;

import java.util.LinkedHashSet;

public class MultiThreadArrayPlaceholder extends SyncArrayPlaceholder {
    // кол-во доступных процессоров
    private final int availableProcessors = Runtime.getRuntime().availableProcessors();

    /**
     * Асинхронное заполнение массива в N потоков (где N - кол-во доступных процессоров)
     */
    public void fill(float[] sourceArray) {
        LinkedHashSet<Thread> threadHashSet = new LinkedHashSet<>();
        int chunkMaxLength = (int) Math.floor(sourceArray.length / this.availableProcessors);
        int chunkCursor = 0;

        while (chunkCursor < sourceArray.length) {
            int chunkStartPosition = chunkCursor;
            int chunkEndPosition = Math.min(chunkStartPosition + chunkMaxLength, sourceArray.length);
            int chunkLength = chunkEndPosition - chunkStartPosition;
            Thread thread = new Thread(() -> this.fillChunk(sourceArray, chunkStartPosition, chunkEndPosition));
            thread.start();
            threadHashSet.add(thread);
            chunkCursor += chunkLength;
        }

        threadHashSet.forEach(action -> {
            try {
                action.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void fillChunk(float[] sourceArray, int startPosition, int endPosition) {
        for (int i = startPosition; i < endPosition; i++) {
            sourceArray[i] = this.calculateItemValue(sourceArray[i], i);
        }
    }
}
