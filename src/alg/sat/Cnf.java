package alg.sat;


import alg.support.Support;
import alg.support.graph.Graph;

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

    public Collection<Literal> variables() {
        Collection<Literal> variables = new HashSet<>();

        for (Clause clause : clauses) {
            Collection<Literal> literals = clause.getLiterals();
            for (Literal literal : literals) {
                if (literal.hasPositiveSign())
                    variables.add(literal);

                if (literal.hasNegativeSign())
                    variables.add(literal.getNegation());

                if (variables.size() == numOfVars)
                    return variables;
            }
        }

        return variables;
    }


    public static Cnf from(int[][] formula, boolean[] assignment) {
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

    public static Cnf from(int[][] formula, int numOfVars) {
        return from(formula, Support.generateDefaultAssignment(numOfVars));
    }

    private Graph<Literal> twoSATGraph() throws IllegalStateException {
        if (!is2SAT())
            throw new IllegalStateException("Not a 2-SAT");

        Graph<Literal> graph = new Graph<>();

        for (int i = 0; i < numOfVars; i++) {
            Literal literal = new Literal(String.valueOf(i + 1), false);
            graph.addNode(literal);
            graph.addNode(literal.getNegation());
        }

        clauses.stream()
                .map(Clause::twoSATGraphEdges)
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

                if (sCC.contains(negation))
                    throw new NoSolutionException("2-SAT has no solution");

                if (!solution.contains(literal)) {
                    literal.setValue(true);

                    if (literal.hasPositiveSign())
                        solution.add(literal);
                }
                if (!solution.contains(negation)) {
                    negation.setValue(false);

                    if (negation.hasPositiveSign())
                        solution.add(negation);
                }
                if (solution.size() == numOfVars)
                    return solution;
            }
        }

        return solution;
    }

    public Collection<Literal> solveHornSAT() {
        Set<HornImplication> hornImplications = clauses.stream()
                .map(Clause::hornImplication)
                .collect(Collectors.toSet());

        hornImplications.stream()
                .filter(HornImplication::isSingleton)
                .forEach(HornImplication::satisfy);

        hornImplications.stream()
                .filter(HornImplication::isImplication)
                .filter(HornImplication::toBeSatisfied)
                .forEach(HornImplication::satisfy);

        if (hornImplications.stream()
                .filter(HornImplication::isPureNegativeClause)
                .anyMatch(HornImplication::toBeSatisfied))
            throw new NoSolutionException("Horn-SAT has no solution");

        return variables();
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
