package algorithms.search;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm{

    protected Queue<AState> opened;
    protected HashSet<AState> closed;
    protected boolean isCostConsidered;

    /**
     * Constructor
     * @param name - String
     */
    public BreadthFirstSearch(String name) {
        super(name);
    }

    /**
     * Constructor
     */
    public BreadthFirstSearch() {
        super("BreadthFirstSearch");
    }

    /**
     * Initializes the data structures that are necessary for the 'solve' method
     */
    protected void initializeDataStructures(){
        opened = new ArrayDeque<>();
        closed = new HashSet<>();
        this.NumberOfNodesEvaluated=0;
        isCostConsidered = false;
    }

    @Override
    public Solution solve(ISearchable searchable) {
        //Initializing the data structures
        initializeDataStructures();
        //Get the starting state
        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();

        opened.add(startState);
        while(opened.size() > 0) {

            AState currentState = opened.remove();
            closed.add(currentState);
            if (currentState.equals(goalState)) {
                NumberOfNodesEvaluated = closed.size();
                return new Solution(currentState);
            }

            //Expand the Node by getting his successors
            ArrayList<AState> successors = searchable.getAllPossibleState(currentState);
            for (AState successorState : successors) {
                if (closed.contains(successorState)) {
                    continue;
                } else if (!opened.contains(successorState)) {
                    //apply the cost of arrival to successorState
                    if (isCostConsidered) {
                        successorState.setCost(successorState.getCost() + currentState.getCost());
                    }
                    //Update successorState parent to be currentState
                    successorState.setParent(currentState);
                    opened.add(successorState);
                }
            }
        }
        return null;
    }

}
