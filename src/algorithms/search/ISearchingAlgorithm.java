package algorithms.search;

public interface ISearchingAlgorithm {

    /**
     * Return the name of the searching algorithm
     * @return - String
     */
    public String getName();

    /**
     * Receives a searchable problem, solves it using the algorithm and returns the solution
     * @param searchable - ISearchable
     * @return - Solution
     */
    public Solution solve(ISearchable searchable);

    /**
     * Return the number of the nodes that the searching algorithm have evaluated in the last run of the 'solve' method
     * @return - int
     */
    public int getNumberOfNodesEvaluated();
}
