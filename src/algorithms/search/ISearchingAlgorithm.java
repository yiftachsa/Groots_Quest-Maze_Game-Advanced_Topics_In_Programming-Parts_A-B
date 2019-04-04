package algorithms.search;

public interface ISearchingAlgorithm {

    public String getName();

    public Solution solve(ISearchable searchable);

    public int getNumberOfNodesEvaluated();
}
