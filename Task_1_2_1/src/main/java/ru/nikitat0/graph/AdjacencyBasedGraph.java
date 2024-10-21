package ru.nikitat0.graph;

import java.util.stream.Collectors;

/**
 * An abstract graph represented by a list of data structures containing
 * information about adjacent vertices.
 */
public abstract class AdjacencyBasedGraph implements Graph {
    @Override
    public boolean equals(Object other) {
        return other instanceof Graph && Graph.equals(this, (Graph) other);
    }

    @Override
    public String toString() {
        StringBuilder head = new StringBuilder();
        StringBuilder body = new StringBuilder();
        forEachVertex((int u) -> {
            head.append(u);
            head.append(' ');
            body.append(getAdjacentVertices(u).stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" ")));
            body.append('\n');
        });
        if (head.length() != 0) {
            head.setCharAt(head.length() - 1, '\n');
            head.append(body);
        }
        return head.toString();
    }
}
