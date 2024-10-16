package ru.nikitat0.graph;

import static ru.nikitat0.graph.Topsort.topsort;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// https://stackoverflow.com/a/16237354
abstract class TopsortTest<T extends Graph> {
    private static int GRAPH_SIZE = 20;

    protected abstract T newGraph();

    @Test
    void testTopsortEmptyGraph() {
        Graph graph = newGraph();
        Assertions.assertIterableEquals(Collections.emptyList(), topsort(graph));
    }

    @Test
    void testTopsortLinkedList() {
        Graph graph = newGraph();
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            vertices.add(graph.addVertex());
        }
        for (int i = 1; i < GRAPH_SIZE; i++) {
            graph.addEdge(vertices.get(i - 1), vertices.get(i));
        }
        Assertions.assertIterableEquals(vertices, topsort(graph));
    }

    @Test
    void testTopsortLinkedListWithForwardEdges() {
        Graph graph = newGraph();
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            vertices.add(graph.addVertex());
        }
        for (int i = 1; i < GRAPH_SIZE; i++) {
            graph.addEdge(vertices.get(i - 1), vertices.get(i));
            if (i + 1 < GRAPH_SIZE) {
                graph.addEdge(
                        vertices.get(i - 1),
                        vertices.get(i + 1 + (i % (GRAPH_SIZE - i - 1))));
            }
        }
        Assertions.assertIterableEquals(vertices, topsort(graph));
    }

    @Test
    void testTopsortTree() {
        Graph graph = newGraph();
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < GRAPH_SIZE; i++) {
            vertices.add(graph.addVertex());
        }
        for (int i = 0; i < GRAPH_SIZE; i++) {
            for (int j : new int[] { 2 * i + 1, 2 * i + 2 }) {
                if (j < GRAPH_SIZE) {
                    graph.addEdge(i, j);
                }
            }
        }
        List<Integer> sorted = topsort(graph);
        Assertions.assertEquals(GRAPH_SIZE, sorted.size());
        BitSet met = new BitSet(GRAPH_SIZE);
        met.set(0);
        Assertions.assertEquals(vertices.get(0), sorted.get(0));
        for (int i = 1; i < sorted.size(); i++) {
            int v = sorted.get(i);
            int u = vertices.get((vertices.indexOf(v) - 1) / 2);
            Assertions.assertTrue(met.get(u));
            met.set(v);
        }
    }

    static class AdjacencyListTopsortTest extends TopsortTest<AdjacencyList> {
        @Override
        protected AdjacencyList newGraph() {
            return new AdjacencyList();
        }
    }

    static class AdjacencyMatrixTopsortTest extends TopsortTest<AdjacencyMatrix> {
        @Override
        protected AdjacencyMatrix newGraph() {
            return new AdjacencyMatrix();
        }
    }

    static class EdgeListTopsortTest extends TopsortTest<EdgeList> {
        @Override
        protected EdgeList newGraph() {
            return new EdgeList();
        }
    }
}
