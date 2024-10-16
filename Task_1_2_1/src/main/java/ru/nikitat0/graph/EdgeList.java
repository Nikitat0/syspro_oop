package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

/**
 * A graph represented by edge list.
 */
public class EdgeList implements Graph {
    private final List<Edge> edges = new ArrayList<>();
    private final BitSet vertices = new BitSet();
    private final IdentifiersCache ids = new IdentifiersCache();

    @Override
    public int addVertex() {
        int id = ids.nextId();
        vertices.set(id);
        return id;
    }

    @Override
    public boolean hasVertex(int u) {
        return vertices.get(u);
    }

    @Override
    public void removeVertex(int u) {
        if (!hasVertex(u)) {
            throw new IllegalArgumentException("Cannot remove vertex: no such vertex");
        }
        this.forEachVertex((int v) -> {
            removeEdge(u, v);
            removeEdge(v, u);
        });
        vertices.clear(u);
    }

    @Override
    public void addEdge(int u, int v) {
        if (!hasVertex(u) || !hasVertex(v)) {
            throw new IllegalArgumentException("Cannot add edge: no such vertices");
        }
        edges.add(new Edge(u, v));
    }

    @Override
    public void removeEdge(int u, int v) {
        if (!hasVertex(u) || !hasVertex(v)) {
            throw new IllegalArgumentException("Cannot remove edge: no such vertices");
        }
        edges.remove(new Edge(u, v));
    }

    @Override
    public int getVertexNumberUpperBound() {
        return vertices.length();
    }

    @Override
    public void forEachVertex(IntConsumer action) {
        for (int u = vertices.nextSetBit(0); u >= 0; u = vertices.nextSetBit(u + 1)) {
            action.accept(u);
        }
    }

    @Override
    public void forEachAdjacent(int u, IntConsumer action) {
        for (Edge edge : edges) {
            if (edge.from == u) {
                action.accept(edge.to);
            }
        }
    }

    private static final class Edge {
        public final int from;
        public final int to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof Edge) && this.from == ((Edge) o).from && this.to == ((Edge) o).to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Edge.class, from, to);
        }
    }
}
