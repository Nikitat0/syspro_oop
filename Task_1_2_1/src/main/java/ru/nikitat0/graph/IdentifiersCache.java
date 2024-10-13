package ru.nikitat0.graph;

import java.util.Arrays;
import java.util.EmptyStackException;

class IdentifiersCache {
    private IntStack ids = new IntStack();

    public IdentifiersCache() {
        this(0);
    }

    public IdentifiersCache(int firstId) {
        ids.push(firstId);
    }

    public int nextId() {
        int id = ids.pop();
        if (ids.isEmpty()) {
            storeId(id + 1);
        }
        return id;
    }

    public void storeId(int id) {
        ids.push(id);
    }
}

class IntStack {
    private int[] data = new int[8];
    private int length = 0;

    public void push(int e) {
        if (length == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[length++] = e;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return data[--length];
    }

    public boolean isEmpty() {
        return length == 0;
    }
}
