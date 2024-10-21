package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * A graph represented by adjacency matrix.
 */
public class AdjacencyMatrix extends AdjacencyBasedGraph {
    private static final int DEFAULT_CAPACITY = 128;

    private IdentifiersCache ids = new IdentifiersCache();
    private ArrayList<BitSet> data;

    /**
     * Loads a graph from the next format: first line contains whitespace-sepatated
     * list of vertices indentifiers, next lines contain list of edges.
     *
     * @param readable source
     * @return loaded graph
     */
    public static AdjacencyMatrix load(Readable readable) {
        AdjacencyMatrix graph = new AdjacencyMatrix();
        try (Scanner lineSc = new Scanner(readable)) {
            if (!lineSc.hasNextLine()) {
                return graph;
            }
            List<Integer> vertices = Arrays.stream(lineSc.nextLine().split("\\s+"))
                    .filter((String s) -> !s.isEmpty())
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            for (int i = 0; i <= Collections.max(vertices); i++) {
                graph.data.add(null);
            }
            for (Integer u : vertices) {
                graph.data.set(u, new BitSet());
            }
            for (Integer u : vertices) {
                Arrays.stream(lineSc.nextLine().split("\\s+"))
                        .filter((String s) -> !s.isEmpty())
                        .map(Integer::valueOf)
                        .forEach((Integer v) -> graph.addEdge(u, v));
            }
        }
        return graph;
    }

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
