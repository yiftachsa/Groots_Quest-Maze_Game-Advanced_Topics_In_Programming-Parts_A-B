package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String searchingAlgorithmName;
    protected int NumberOfNodesEvaluated;

    /**
     * Constructor
     * @param name - String - The name of the algorithm
     */
    public ASearchingAlgorithm(String name) {
        this.searchingAlgorithmName = name;
        this.NumberOfNodesEvaluated = 0;
    }

    @Override
    public String getName() {
        return this.searchingAlgorithmName;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return this.NumberOfNodesEvaluated;
    }

}
