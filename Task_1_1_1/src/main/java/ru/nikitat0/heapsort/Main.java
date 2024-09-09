package ru.nikitat0.heapsort;

import java.util.Arrays;

/** Contains all program function. */
public class Main {
    /**
     * Entry point.
     *
     * @param args Program arguments
     */
    public static void main(String[] args) {
        int[] array = new int[] { 5, 4, 3, 2, 1 };
        heapsort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * Sorts an array.
     * 
     * @param array Array to sort
     */
    public static void heapsort(int[] array) {
        makeHeap(array);
        for (int i = array.length - 1; i > 0; i--) {
            int j = 0;
            int e = array[i];
            array[i] = array[0];
            array[j] = e;
            while (true) {
                int k = j;
                int l = j * 2 + 1;
                int r = j * 2 + 2;
                if (l < i && array[l] > array[k]) {
                    k = l;
                }
                if (r < i && array[r] > array[k]) {
                    k = r;
                }
                if (j == k) {
                    break;
                }
                array[j] = array[k];
                array[k] = e;
                j = k;
            }
        }
    }

    /**
     * Constructs a binary heap from an array in-place.
     * 
     * @param array Array with elements to make the heap
     */
    public static void makeHeap(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int j = i;
            int e = array[j];
            while (j != 0 && array[(j - 1) / 2] < e) {
                array[j] = array[(j - 1) / 2];
                array[(j - 1) / 2] = e;
                j = (j - 1) / 2;
            }
        }
    }
}
