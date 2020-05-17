package alg.sat.graph;

import java.util.Collection;

public class Node {
    private Collection<Edge> edges;

    public Edge connectTo(Node node) {
        Edge edge = new Edge(this, node);
        edges.add(edge);
        return edge;
    }
}
