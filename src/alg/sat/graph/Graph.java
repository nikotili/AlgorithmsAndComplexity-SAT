package alg.sat.graph;

import alg.sat.Literal;

import java.util.*;

public class Graph<N> {
    private Set<N> nodes;
    private final Map<N, Set<N>> map;

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
                visited.add(node);
                stronglyConnectedComponents.put(scc, explore(node));
                scc++;
            }
        }

        return stronglyConnectedComponents;
    }

    private Set<N> explore(N node) {

        Deque<N> toBeExplored = new LinkedList<>();
        toBeExplored.add(node);
        Set<N> explored = new HashSet<>();

        while (!toBeExplored.isEmpty()) {
            N n = toBeExplored.poll();
            preVisit.put(n, clock++);
            if (!explored.contains(n)) {
                explored.add(n);
                visited.add(n);
                map.get(n)
                        .stream()
                        .filter(n1 -> !explored.contains(n1) && !visited.contains(n1))
                        .forEach(toBeExplored::push);
            }
            postVisit.put(n, clock++);
        }

        return explored;
    }


    //todo maybe clone
    public Graph<N> createReverse() {
        Graph<N> reversedGraph = new Graph<>();
        this.nodes.forEach(reversedGraph::addNode);

        this.map.forEach((u, adjSet) -> adjSet.forEach(v -> reversedGraph.addEdge(v, u)));

        return reversedGraph;
    }

    public Map<Integer, Set<N>> getSCCs() {
        Graph<N> reverse = this.createReverse();
        reverse.dfs();
        Map<N, Integer> reversePostVisit = reverse.getPostVisit();
        Integer reverseClock = reverse.getClock();
        Object[] objects = new Object[reverseClock];

        reversePostVisit.forEach((n, postValue) -> objects[postValue] = n);
        this.nodes = new LinkedHashSet<>();
        for (int i = objects.length - 1; i >= 0; i--) {
            if (objects[i] != null)
                this.addNode((N) objects[i]);
        }

        return this.dfs();
    }

//    public static <N> Map<Integer, Set<N>> getSCCs(Graph<N> graph) {
//        return graph
////                .createReverse()
//                .dfs();
//    }
}
