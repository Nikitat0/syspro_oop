package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.function.IntConsumer;

/**
 * A graph represented by adjacency matrix.
 */
public class AdjacencyMatrix implements Graph {
    private static final int DEFAULT_CAPACITY = 128;

    private IdentifiersCache ids = new IdentifiersCache();
    private ArrayList<BitSet> data;

    public AdjacencyMatrix() {
        this(DEFAULT_CAPACITY);
    }

    public AdjacencyMatrix(int capacity) {
        data = new ArrayList<>(capacity);
    }

    @Override
    public int addVertex() {
        int id = ids.nextId();
        if (id == data.size()) {
            data.add(id, new BitSet());
        } else {
            data.set(id, new BitSet());
        }
        return id;
    }

    @Override
    public boolean hasVertex(int u) {
        return u < data.size() && data.get(u) != null;
    }

    @Override
    public void removeVertex(int u) {
        if (!hasVertex(u)) {
            throw new IllegalArgumentException("Cannot remove vertex: no such vertex");
        }
        forEachVertex((int v) -> removeEdge(v, u));
        data.set(u, null);
        ids.storeId(u);
    }

    @Override
    public void addEdge(int u, int v) {
        if (!hasVertex(u) || !hasVertex(v)) {
            throw new IllegalArgumentException("Cannot add edge: no such vertices");
        }
        data.get(u).set(v);
    }

    @Override
    public void removeEdge(int u, int v) {
        if (!hasVertex(u) || !hasVertex(v)) {
            throw new IllegalArgumentException("Cannot remove edge: no such vertices");
        }
        data.get(u).clear(v);
    }

    @Override
    public int getVertexNumberUpperBound() {
        return data.size();
    }

    @Override
    public void forEachVertex(IntConsumer action) {
        for (int u = 0; u < data.size(); u++) {
            if (data.get(u) != null) {
                action.accept(u);
            }
        }
    }

    @Override
    public void forEachAdjacent(int u, IntConsumer action) {
        BitSet bs = data.get(u);
        for (int v = bs.nextSetBit(0); v != -1; v = bs.nextSetBit(v + 1)) {
            action.accept(v);
        }
    }
}
