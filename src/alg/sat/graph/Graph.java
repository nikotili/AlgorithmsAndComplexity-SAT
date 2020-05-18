package alg.sat.graph;

import java.util.*;

public class Graph<N> {
    private Set<N> nodes;
    private Map<N, Set<N>> map;

    public Graph() {
        this.nodes = new HashSet<>();
        this.map = new HashMap<>();
    }

    public void setNodes(Set<N> nodes) {
        this.nodes = nodes;
    }

    public void addNode(N node) {
        nodes.add(node);
        map.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(N u, N v) {
        map.get(u).add(v);
    }

    public void addEdges(Map<N, N> edges) {
        edges.forEach(this::addEdge);
    }

    private Set<N> visited;
    private N[] preVisit;
    private N[] postVisit;
    private Integer clock;

    public N[] getPostVisit() {
        return postVisit;
    }

    public Integer getClock() {
        return clock;
    }

    public Map<Integer, Set<N>> dfs() {
        clock = 1;
        int prePostSize = nodes.size() * 2 + 1;
        preVisit = (N[]) new Object[prePostSize];
        postVisit = (N[]) new Object[prePostSize];
        visited = new HashSet<>();
        Map<Integer, Set<N>> stronglyConnectedComponents = new HashMap<>();
        Integer scc = 1;

        for (N node : nodes) {
            if (!visited.contains(node)) {
                stronglyConnectedComponents.put(scc, explore(node));
                scc++;
            }
        }

        return stronglyConnectedComponents;
    }

    private Set<N> explore(N node) {
        Set<N> explored = new HashSet<>();
        explored.add(node);
        visited.add(node);
        preVisit[clock++] = node;

        map.get(node)
                .stream()
                .filter(v -> !visited.contains(v))
                .forEach(v -> explored.addAll(explore(v)));

        postVisit[clock++] = node;

        return explored;
    }

    public Graph<N> createReverse() {
        Graph<N> reversedGraph = new Graph<>();
        this.nodes.forEach(reversedGraph::addNode);

        this.map.forEach((u, adjSet) -> adjSet.forEach(v -> reversedGraph.addEdge(v, u)));

        return reversedGraph;
    }

    public Map<Integer, Set<N>> sCCs() {
        Graph<N> reverse = this.createReverse();
        reverse.dfs();
        N[] reversePostVisit = reverse.getPostVisit();

        this.nodes = new LinkedHashSet<>();
        for (int i = reversePostVisit.length - 1; i >= 0; i--) {
            if (reversePostVisit[i] != null)
                this.addNode(reversePostVisit[i]);
        }

        return this.dfs();
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");
        graph.addNode("H");
        graph.addNode("I");
        graph.addNode("J");
        graph.addNode("K");
        graph.addNode("L");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "F");
        graph.addEdge("E", "B");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");
        graph.addEdge("F", "C");
        graph.addEdge("F", "H");
        graph.addEdge("G", "H");
        graph.addEdge("G", "J");
        graph.addEdge("H", "K");
        graph.addEdge("I", "G");
        graph.addEdge("J", "I");
        graph.addEdge("K", "L");
        graph.addEdge("L", "J");

        System.out.println(graph.sCCs());

    }
}
