package ru.nikitat0.heapsort;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {
    static int[] sampleArray() {
        Random rd = new Random();
        int[] sample = new int[100];
        for (int i = 0; i < 100; i++) {
            sample[i] = rd.nextInt();
        }
        return sample;
    }

    @Test
    void testMakeHeap() {
        int[] sample = sampleArray();
        Main.makeHeap(sample);
        for (int i = 1; i < sample.length; i++) {
            Assertions.assertTrue(sample[i] <= sample[(i - 1) / 2], "Heap invariant is violated");
        }
    }

    @Test
    void testHeapsort() {
        int[] expected = sampleArray();
        int[] actual = expected.clone();
        Arrays.sort(expected);
        Main.heapsort(actual);
        Assertions.assertArrayEquals(expected, actual);
    }
}
