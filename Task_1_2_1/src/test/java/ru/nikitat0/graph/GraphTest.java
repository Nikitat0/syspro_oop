package ru.nikitat0.graph;

import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;
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

    protected abstract String graphStr();

    protected abstract T loadGraph(Readable readable);

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
                adjacentToList(graph, vertices.get(0)));
        Assertions.assertIterableEquals(
                Stream.of(vertices.get(2)).sorted().collect(Collectors.toList()),
                adjacentToList(graph, vertices.get(1)));

        graph.removeVertex(vertices.pop());
        Assertions.assertEquals(vertices, verticesToList(graph));
        Assertions.assertIterableEquals(
                Stream.of(vertices.get(1)).sorted().collect(Collectors.toList()),
                adjacentToList(graph, vertices.get(0)));
        Assertions.assertTrue(adjacentToList(graph, vertices.get(1)).isEmpty());

        graph.removeEdge(vertices.get(0), vertices.get(1));
        Assertions.assertEquals(vertices, verticesToList(graph));
        Assertions.assertTrue(adjacentToList(graph, vertices.get(0)).isEmpty());
        Assertions.assertTrue(adjacentToList(graph, vertices.get(1)).isEmpty());

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

    @Test
    void testLoadEmpty() {
        Assertions.assertEquals(
                Collections.emptyList(),
                verticesToList(loadGraph(new Readable() {
                    @Override
                    public int read(CharBuffer cb) throws IOException {
                        return -1;
                    }
                })));
    }

    @Test
    void testLoad() {
        Graph graph = loadGraph(new StringReader(graphStr()));
        Assertions.assertIterableEquals(
                Stream.of(2, 3, 5).collect(Collectors.toList()),
                verticesToList(graph));
        Assertions.assertIterableEquals(
                Stream.of(3, 5).collect(Collectors.toList()),
                adjacentToList(graph, 2));
        Assertions.assertIterableEquals(
                Stream.of(5).collect(Collectors.toList()),
                adjacentToList(graph, 3));
        Assertions.assertIterableEquals(
                Collections.emptyList(),
                adjacentToList(graph, 5));
    }

    private static List<Integer> verticesToList(Graph graph) {
        ArrayList<Integer> vertices = new ArrayList<>();
        graph.forEachVertex((int u) -> vertices.add(u));
        Collections.sort(vertices);
        return vertices;
    }

    private static List<Integer> adjacentToList(Graph graph, int u) {
        ArrayList<Integer> vertices = new ArrayList<>();
        graph.forEachAdjacent(u, (int v) -> vertices.add(v));
        Collections.sort(vertices);
        return vertices;
    }

    static class AdjacencyListGraphTest extends GraphTest<AdjacencyList> {
        @Override
        protected AdjacencyList newGraph() {
            return new AdjacencyList();
        }

        @Override
        protected String graphStr() {
            StringBuilder sb = new StringBuilder();
            sb.append("5 3 2\n");
            sb.append("\n");
            sb.append("5\n");
            sb.append("5 3");
            return sb.toString();
        }

        @Override
        protected AdjacencyList loadGraph(Readable readable) {
            return AdjacencyList.load(readable);
        }
    }

    static class AdjacencyMatrixGraphTest extends GraphTest<AdjacencyMatrix> {
        @Override
        protected AdjacencyMatrix newGraph() {
            return new AdjacencyMatrix();
        }

        @Override
        protected AdjacencyMatrix loadGraph(Readable readable) {
            return AdjacencyMatrix.load(readable);
        }

        @Override
        protected String graphStr() {
            StringBuilder sb = new StringBuilder();
            sb.append("5 3 2\n");
            sb.append("\n");
            sb.append("5\n");
            sb.append("5 3");
            return sb.toString();
        }
    }

    static class EdgeListGraphTest extends GraphTest<EdgeList> {

        @Override
        protected EdgeList newGraph() {
            return new EdgeList();
        }

        @Override
        protected EdgeList loadGraph(Readable readable) {
            return EdgeList.load(readable);
        }

        @Override
        protected String graphStr() {
            StringBuilder sb = new StringBuilder();
            sb.append("2 3 5\n");
            sb.append("2 3\n");
            sb.append("2 5\n");
            sb.append("3 5");
            return sb.toString();
        }
    }
}
