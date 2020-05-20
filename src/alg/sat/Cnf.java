package alg.sat;


import alg.support.Support;
import alg.support.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a CNF (Conjunctive Normal Form) formula
 */
public class Cnf implements Valuable {
    private final Collection<Clause> clauses;
    private final int numOfVars;

    private Cnf(int numOfVars) {
        this.numOfVars = numOfVars;
        clauses = new ArrayList<>();
    }

    public boolean value() {
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

    /**
     * Returns the variables composing the cnf
     * @return a {@link Collection} of {@link Literal}-s representing variables of
     * the instance and their current values
     */
    private Collection<Literal> variables() {
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


    /**
     * @param formula
     * @param assignment
     * @return an instance of this class
     */
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

    /**
     * @param formula represented by an matrix of int-s
     * @param numOfVars composing the formula
     * @return an instance of this class with every variable set to {@code false}
     */
    public static Cnf from(int[][] formula, int numOfVars) {
        return from(formula, Support.generateDefaultAssignment(numOfVars));
    }


    /**
     * Creates a graph representation of the instance.
     * @return {@link Graph<Literal>} instance containing all variables and their negations
     * which is used by {@link #solve2SAT()} to find a satisfiable assignment for
     * this instance.
     * @throws IllegalStateException if the instance is not a 2-SAT
     */
    //todo test
    private Graph<Literal> twoSATGraph() throws IllegalStateException {
        if (!is2SAT())
            throw new IllegalStateException("Instance is not a 2-SAT");

        Graph<Literal> graph = new Graph<>();
        Collection<Literal> variables = this.variables();

        for (Literal literal: variables) {
            graph.addNode(literal);
            graph.addNode(literal.getNegation());
        }

        clauses.stream()
                .map(Clause::twoSATGraphEdges)
                .forEach(graph::addEdges);

        return graph;
    }

    /**
     * Solves the cnf represented by the instance and produces the assignment for the variables.
     * If the instance is not a 2-SAT or there is no solution, the method will throw an exception.
     *
     * @return a {@link Collection} of {@link Literal} which are the variables with their
     * assignments.
     * @throws NoSolutionException if the 2-SAT has no solution
     * @throws IllegalStateException if the instance is not a 2-SAT
     */
    public Collection<Literal> solve2SAT() throws NoSolutionException {
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

    /**
     * Solves the cnf represented by the instance and produces the assignment for the variables.
     * If the instance is not a Horn-SAT or there is no solution, the method will throw an exception.
     *
     * @return a {@link Collection} of {@link Literal} which are the variables with their
     * assignments.
     * @throws NoSolutionException if the Horn-SAT has no solution
     * @throws IllegalStateException if the instance is not a Horn-SAT
     */
    public Collection<Literal> solveHornSAT() throws NoSolutionException {
        if (!this.isHornSAT())
            throw new IllegalStateException("Instance is not a Horn-SAT");
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


    /**
     * Thrown to indicate that a Cnf has no solution
     */
    private static class NoSolutionException extends RuntimeException {
        public NoSolutionException(String message) {
            super(message);
        }
    }
}
