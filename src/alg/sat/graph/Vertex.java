package alg.sat.graph;

import java.util.Collection;
import java.util.Objects;

public class Vertex {
    private String name;
    private Collection<Edge> edges;


    public Edge connectTo(Vertex vertex) {
        Edge edge = new Edge(this, vertex);
        edges.add(edge);
        return edge;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
