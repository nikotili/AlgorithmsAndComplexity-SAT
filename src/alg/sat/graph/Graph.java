package alg.sat.graph;

import alg.sat.Literal;

import java.util.*;

public class Graph<N> {
    private Set<N> nodes;
    private Map<N, Set<N>> map;

    public Graph() {
        this.nodes = new LinkedHashSet<>();
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

    public void addEdges(N from, Collection<N> adjList) {
        map.get(from).addAll(adjList);
    }

    private Set<N> visited;
    //todo with array
    private Map<N, Integer> preVisit;
    private Map<N, Integer> postVisit;
    private Integer clock;

    public Map<N, Integer> getPostVisit() {
        return postVisit;
    }

    public Integer getClock() {
        return clock;
    }

    public Map<Integer, Set<N>> dfs() {
        clock = 1;
        preVisit = new HashMap<>();
        postVisit = new HashMap<>();
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
        preVisit.put(node, clock++);

        map.get(node)
                .stream()
                .filter(v -> !visited.contains(v))
                .forEach(v -> explored.addAll(explore(v)));

        postVisit.put(node, clock++);

        return explored;
    }


    //todo maybe clone
    public Graph<N> createReverse() {
        Graph<N> reversedGraph = new Graph<>();
        this.nodes.forEach(reversedGraph::addNode);

        this.map.forEach((u, adjSet) -> adjSet.forEach(v -> reversedGraph.addEdge(v, u)));

        return reversedGraph;
    }

    public Map<Integer, Set<N>> SCCs() {

        Graph<N> reverse = this.createReverse();
        reverse.dfs();
        Map<N, Integer> reversePostVisit = reverse.getPostVisit();
        Integer reverseClock = reverse.getClock();
        N[] nodesPrime = (N[]) new Object[reverseClock];

        reversePostVisit.forEach((literal, postValue) -> nodesPrime[postValue] = literal);

        this.nodes = new LinkedHashSet<>();
        this.map = new HashMap<>();
        for (int i = nodesPrime.length - 1; i >= 0; i--) {
            if (nodesPrime[i] != null)
                this.addNode(nodesPrime[i]);
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

        graph.addEdge("A", "B");
        graph.addEdge("B", "A");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
        graph.addEdge("F", "D");


//        Graph<String> reverse = graph.createReverse();
        Graph<String> reverse = graph.createReverse();
        System.out.println(reverse.dfs());
        System.out.println(reverse.preVisit);
        System.out.println(reverse.postVisit);

    }

//    public static <N> Map<Integer, Set<N>> getSCCs(Graph<N> graph) {
//        return graph
////                .createReverse()
//                .dfs();
//    }
}
