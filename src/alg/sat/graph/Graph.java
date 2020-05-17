package alg.sat.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

public class Graph {
    private Collection<Vertex> vertices;

    public Graph() {
        this.vertices = new HashSet<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }
}
