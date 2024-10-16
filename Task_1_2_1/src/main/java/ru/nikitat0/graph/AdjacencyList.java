package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/** A graph represented by adjacency list. */
public final class AdjacencyList implements Graph {
    private IdentifiersCache ids = new IdentifiersCache();
    private List<List<Integer>> data = new ArrayList<>();

    @Override
    public int addVertex() {
        int id = ids.nextId();
        if (data.size() == id) {
            data.add(new ArrayList<>());
        } else {
            data.set(id, new ArrayList<>());
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
        data.get(u).add(v);
    }

    @Override
    public void removeEdge(int u, int v) {
        if (!hasVertex(u) || !hasVertex(v)) {
            throw new IllegalArgumentException("Cannot remove edge: no such vertices");
        }
        data.get(u).remove((Integer) v);
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
        for (int v : data.get(u)) {
            action.accept(v);
        }
    }
}
