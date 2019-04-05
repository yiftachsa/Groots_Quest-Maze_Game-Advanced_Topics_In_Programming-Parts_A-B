package algorithms.search;

import java.util.*;

import java.util.concurrent.ArrayBlockingQueue;


public class DepthFirstSearch extends ASearchingAlgorithm{

    private Stack<AState> colored;
    private HashSet<AState> discovered;

    private boolean isCostConsidered;

    /**
     * Constructor
     */
    public DepthFirstSearch() {
        super("DepthFirstSearch");
    }

    /**
     * Initializes the data structures that are necessary for the 'solve' method
     */
    private void initializeDataStructures() {
        colored = new Stack<>();
        discovered = new HashSet<>();
        this.NumberOfNodesEvaluated=0;
        isCostConsidered=false;
    }

    @Override
    public Solution solve(ISearchable searchable) {

        initializeDataStructures();
        AState goalState = searchable.getGoalState();
        colored.push(searchable.getStartState());
        while(!colored.empty())
        {
            AState currentState = colored.pop();
            if(currentState.equals(goalState))
            {
                NumberOfNodesEvaluated = discovered.size();
                return new Solution(currentState);//###OR SOLUTION
            }
            if( !discovered.contains(currentState)) {
                discovered.add(currentState);
            }
            ArrayList<AState> successors = searchable.getAllPossibleState(currentState);
            for (AState successorState : successors) {
                if(!discovered.contains(successorState)) {
                    successorState.setParent(currentState);
                    colored.push(successorState);
                }
            }



        }
        return null;
    }



}



