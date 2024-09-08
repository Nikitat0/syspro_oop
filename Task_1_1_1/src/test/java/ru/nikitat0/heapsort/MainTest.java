package ru.nikitat0.heapsort;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final int[] EMPTY_ARRAY = new int[] {};
    private static final int SAMPLE_ARRAY_SIZE = 100;

    private static int[] sampleArray(final int length) {
        Random rd = new Random();
        int[] sample = new int[length];
        for (int i = 0; i < length; i++) {
            sample[i] = rd.nextInt();
        }
        return sample;
    }

    @Test
    void testMakeHeap() {
        int[] sample = sampleArray(SAMPLE_ARRAY_SIZE);
        Main.makeHeap(sample);
        for (int i = 1; i < sample.length; i++) {
            Assertions.assertTrue(sample[i] <= sample[(i - 1) / 2], "Heap invariant is violated");
        }
    }

    @Test
    void testHeapsort() {
        int[] expected = sampleArray(SAMPLE_ARRAY_SIZE);
        int[] actual = expected.clone();
        Arrays.sort(expected);
        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);

        expected = new int[] { 2, 2, 2, 2, 2 };
        actual = expected.clone();
        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testWithEmptyArrays() {
        Main.makeHeap(EMPTY_ARRAY);
        Main.heapsort(EMPTY_ARRAY);
    }
}
