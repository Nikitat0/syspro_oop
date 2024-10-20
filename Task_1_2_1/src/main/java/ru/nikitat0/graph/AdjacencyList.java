package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/** A graph represented by adjacency list. */
public final class AdjacencyList extends AdjacencyBasedGraph {
    private static final int DEFAULT_CAPACITY = 128;

    private IdentifiersCache ids = new IdentifiersCache();
    private List<Set<Integer>> data = new ArrayList<>();

    /**
     * Loads a graph from the next format: first line contains whitespace-sepatated
     * list of vertices indentifiers, next lines contain list of edges.
     *
     * @param readable source
     * @return loaded graph
     */
    public static AdjacencyList load(Readable readable) {
        AdjacencyList graph = new AdjacencyList();
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
                graph.data.set(u, new TreeSet<>());
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

    public AdjacencyList() {
        this(DEFAULT_CAPACITY);
    }

    public AdjacencyList(int capacity) {
        data = new ArrayList<>(capacity);
    }

    @Override
    public int addVertex() {
        int id = ids.nextId();
        if (data.size() == id) {
            data.add(new TreeSet<>());
        } else {
            data.set(id, new TreeSet<>());
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AdjacencyList)) {
            return false;
        }
        AdjacencyList other = (AdjacencyList) o;
        int commonLen = Math.min(this.data.size(), other.data.size());
        for (AdjacencyList graph : new AdjacencyList[] { this, other }) {
            for (int i = commonLen; i < graph.data.size(); i++) {
                if (graph.data.get(i) != null) {
                    return false;
                }
            }
        }
        return this.data.subList(0, commonLen).equals(other.data.subList(0, commonLen));
    }
}
