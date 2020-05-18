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

    public Boolean getValue() {
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
            literals[i] = new Literal(String.valueOf(i + 1));
        }

        Cnf cnf = new Cnf(length);

        for (int[] clause : formula) {
            Clause clauseObj = Clause.from(literals, clause);
            cnf.clauses.add(clauseObj);
        }

        return cnf;
    }

    public static Cnf from(int[][] formula, int numOfVars) {
        return from(formula, Support.generateDefaultAssignment(numOfVars));
    }

    private Graph<Literal> twoSATGraph() throws IllegalStateException {
        if (!is2SAT())
            throw new IllegalStateException("Not a 2-SAT");

        Graph<Literal> graph = new Graph<>();

        for (int i = 0; i < numOfVars; i++) {
            Literal literal = new Literal(String.valueOf(i + 1));
            graph.addNode(literal);
            graph.addNode(literal.getNegation());
        }

        clauses.stream()
                .map(Clause::get2SATGraphEdges)
                .forEach(graph::addEdges);

        return graph;
    }

    public Collection<Literal> solve2SAT() {
        Graph<Literal> literalGraph = this.twoSATGraph();
        Collection<Set<Literal>> sCCs = literalGraph.sCCs().values();
        Collection<Literal> solution = new HashSet<>(numOfVars);
        for (Set<Literal> sCC : sCCs) {
            for (Literal literal : sCC) {
                Literal negation = literal.getNegation();

                //todo maybe other method
                if (sCC.contains(negation))
                    throw new NoSolutionException("2-SAT has no solution");

                if (!solution.contains(literal)) {
                    literal.setValue(Boolean.TRUE);

                    if (literal.isPositiveLiteral())
                        solution.add(literal);
                }
                if (!solution.contains(negation)) {
                    negation.setValue(Boolean.FALSE);

                    if (negation.isPositiveLiteral())
                        solution.add(negation);
                }
                if (solution.size() == numOfVars)
                    return solution;
            }
        }

        return solution;
    }

    @Override
    public String toString() {
        return this.clauses
                .stream()
                .map(Clause::toString)
                .collect(Collectors.joining(" and "));
    }


    private static class NoSolutionException extends RuntimeException {
        public NoSolutionException(String message) {
            super(message);
        }
    }
}
