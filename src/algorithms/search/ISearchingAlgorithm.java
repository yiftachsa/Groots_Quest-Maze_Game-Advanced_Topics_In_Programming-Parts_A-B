package algorithms.search;

public interface ISearchingAlgorithm {

    public String getName();

    public AState solve(ISearchable searchable);

    public int getNumberOfNodesEvaluated();
}
