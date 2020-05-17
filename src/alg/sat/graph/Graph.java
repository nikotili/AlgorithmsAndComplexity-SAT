package alg.sat.graph;

import java.util.*;

public class Graph<N> {
    private Collection<N> nodes;
    private Map<N, Collection<N>> map;

    public Graph() {
        this.nodes = new HashSet<>();
        this.map = new HashMap<>();
    }


    public void addNode(N node) {
        nodes.add(node);
        map.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(N node, N adjNode) {
        map.get(node).add(adjNode);
    }

    public void addEdges(N from, Collection<N> adjList) {
        map.get(from).addAll(adjList);
    }
}
