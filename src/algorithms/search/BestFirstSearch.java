package algorithms.search;

import java.util.HashSet;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{
    public BestFirstSearch() {
        super("BestFirstSearch");
    }

    @Override
    protected void initializeDataStructures() {
        opened = new PriorityQueue<>();
        closed = new HashSet<>();
        this.NumberOfNodesEvaluated = 0;
        isCostConsidered = true;

    }



}
