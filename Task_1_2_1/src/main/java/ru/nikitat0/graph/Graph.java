package ru.nikitat0.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * A graph data structure with basic operations for managing vertices and edges.
 */
public interface Graph {
    /**
     * Adds a new vertex to the graph.
     *
     * @return the identifier of the added vertex
     */
    int addVertex();

    /**
     * Returns true if graph contains vertex with given number.
     *
     * @param u the number of vertex.
     *
     * @return true if graph contains vertex with given number
     */
    boolean hasVertex(int u);

    /**
     * Removes a vertex from the graph.
     *
     * @param u the identifier of the vertex
     *
     * @throws IllegalArgumentException if the vertex does not exist
     */
    void removeVertex(int u);

    /**
     * Adds a new edge to the graph. Does nothing if the edge already exists.
     *
     * @param u the identifier of the first vertex
     * @param v the identifier of the second vertex
     *
     * @throws IllegalArgumentException if either {@code u} or {@code v} doesn't
     *                                  belongs to the graph
     */
    void addEdge(int u, int v);

    /**
     * Removes an edge from the graph. Does nothing if there is no such edge.
     *
     * @param u the identifier of the first vertex
     * @param v the identifier of the second vertex
     *
     * @throws IllegalArgumentException if either {@code u} or {@code v} doesn't
     *                                  belongs to the graph
     */
    void removeEdge(int u, int v);

    /**
     * Returns the current upper bound for vertex number.
     *
     * @return the current upper bound for vertex number
     */
    int getVertexNumberUpperBound();

    /**
     * Applies the given action to each vertex until all vertices have been
     * processed or the action
     * throws an exception.
     *
     * @param action the action to be performed for each element
     */
    void forEachVertex(IntConsumer action);

    /**
     * Applies the given action to each vertex adjacent to the specified vertex.
     *
     * @param u      the vertex identifier
     * @param action the action to perform on each adjacent vertex
     */
    void forEachAdjacent(int u, IntConsumer action);

    /**
     * Returns a list of vertices adjacent to the given vertex.
     *
     * @param u the given vertex
     * @return list of vertices adjacent to the given vertex
     */
    default List<Integer> getAdjacentVertices(int u) {
        ArrayList<Integer> vertices = new ArrayList<>();
        this.forEachAdjacent(u, (int v) -> vertices.add(v));
        return vertices;
    }
}
