package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// https://stackoverflow.com/a/16237354
abstract class GraphTest<T extends Graph> {
    protected abstract T newGraph();

    @Test
    void testGraphCreating() {
        Graph graph = newGraph();
        Stack<Integer> vertices = new Stack<>();
        for (int i = 0; i < 3; i++) {
            vertices.add(graph.addVertex());
            Assertions.assertEquals(vertices, verticesToList(graph));
        }

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(0), vertices.get(2));
        graph.addEdge(vertices.get(1), vertices.get(2));
        Assertions.assertIterableEquals(
                Stream.of(vertices.get(1), vertices.get(2)).sorted().collect(Collectors.toList()),
                graph.getAdjacentVertices(vertices.get(0)));
        Assertions.assertIterableEquals(
                Stream.of(vertices.get(2)).sorted().collect(Collectors.toList()),
                graph.getAdjacentVertices(vertices.get(1)));

        graph.removeVertex(vertices.pop());
        Assertions.assertEquals(vertices, verticesToList(graph));
        Assertions.assertIterableEquals(
                Stream.of(vertices.get(1)).sorted().collect(Collectors.toList()),
                graph.getAdjacentVertices(vertices.get(0)));
        Assertions.assertTrue(graph.getAdjacentVertices(vertices.get(1)).isEmpty());

        graph.removeEdge(vertices.get(0), vertices.get(1));
        Assertions.assertEquals(vertices, verticesToList(graph));
        Assertions.assertTrue(graph.getAdjacentVertices(vertices.get(0)).isEmpty());
        Assertions.assertTrue(graph.getAdjacentVertices(vertices.get(1)).isEmpty());

        for (int i = 0; i < 2; i++) {
            graph.removeVertex(vertices.pop());
            Assertions.assertEquals(vertices, verticesToList(graph));
        }
    }

    @Test
    void testGraphInappropriateUse() {
        Graph graph = newGraph();
        Assertions.assertThrows(IllegalArgumentException.class, () -> graph.removeVertex(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> graph.addEdge(0, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(0, 1));
    }

    private static List<Integer> verticesToList(Graph graph) {
        ArrayList<Integer> vertices = new ArrayList<>();
        graph.forEachVertex((int u) -> vertices.add(u));
        Collections.sort(vertices);
        return vertices;
    }

    static class AdjacencyListTopsortTest extends GraphTest<AdjacencyList> {
        @Override
        protected AdjacencyList newGraph() {
            return new AdjacencyList();
        }
    }

    static class AdjacencyMatrixTopsortTest extends GraphTest<AdjacencyMatrix> {
        @Override
        protected AdjacencyMatrix newGraph() {
            return new AdjacencyMatrix();
        }
    }
}
