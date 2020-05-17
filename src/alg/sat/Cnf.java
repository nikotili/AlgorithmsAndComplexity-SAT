package alg.sat;


import alg.sat.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class Cnf implements Valuable {
    private final Collection<Clause> clauses;
    private final int numOfVars;

    private Cnf(int numOfVars) {
        this.numOfVars = numOfVars;
        clauses = new ArrayList<>();
    }

    public boolean getValue() {
        return clauses
                .stream()
                .noneMatch(Clause::isFalse);
    }

    public boolean is2SAT() {
        return clauses
                .stream()
                .noneMatch(Clause::hasMoreThanTwoLiterals);
    }

    public boolean isHornSAT() {
        return clauses
                .stream()
                .allMatch(Clause::hasAtMostOnePositiveLiteral);
    }


    public static Cnf from(int[][] formula, Boolean[] assignment) {
        int length = assignment.length;

        Literal[] literals = new Literal[length];
        for (int i = 0; i < length; i++) {
            literals[i] = new Literal(String.valueOf(i + 1), assignment[i]);
        }

        Cnf cnf = new Cnf(length);

        for (int[] clause : formula) {
            Clause clauseObj = Clause.from(literals, clause);
            cnf.clauses.add(clauseObj);
        }

        return cnf;
    }

    public static Cnf defaultFrom(int[][] formula, int numOfVars) {
        return from(formula, Support.generateDefaultAssignment(numOfVars));
    }

    public Graph<Literal> to2SATGraph() {
        Graph<Literal> graph = new Graph<>();

        for (int i = 0; i < numOfVars; i++) {
            Literal literal = new Literal(String.valueOf(i + 1), Boolean.TRUE);
            graph.addNode(literal);
            graph.addNode(literal.getNegation());
        }

        clauses.stream()
                .map(Clause::get2SATGraphEdges)
                .forEach(map -> map.forEach(graph::addEdge));

        return graph;
    }

    @Override
    public String toString() {
        return this.clauses
                .stream()
                .map(Clause::toString)
                .collect(Collectors.joining(" and "));
    }
}
