package ru.nikitat0.graph;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class contains a static method to topologically sort an graph.
 */
public final class Topsort {
    final Graph graph;
    final List<Integer> sorted;
    final BitSet used;
    int pos;

    /**
     * Returns a list of vertices in topological order for the given graph.
     * DFS-based algorithm is used.
     *
     * @param graph the graph to be sorted
     * @return list of vertices in topological order
     */
    public static List<Integer> topsort(Graph graph) {
        return new Topsort(graph).perform();
    }

    private Topsort(Graph graph) {
        final int n = graph.getVertexNumberUpperBound() + 1;
        this.graph = graph;
        this.sorted = Stream.generate(() -> (Integer) null).limit(n).collect(Collectors.toList());
        this.used = new BitSet(n);
        this.pos = n - 1;
    }

    private List<Integer> perform() {
        final int n = graph.getVertexNumberUpperBound();
        for (int u = 0; u <= n; u++) {
            if (graph.hasVertex(u)) {
                topsortDfs(u);
            }
        }
        return Collections.unmodifiableList(sorted.subList(pos + 1, sorted.size()));
    }

    private void topsortDfs(int u) {
        if (used.get(u)) {
            return;
        }
        used.set(u);
        graph.forEachAdjacent(u, (int v) -> topsortDfs(v));
        sorted.set(pos--, u);
    }
}
