package algorithms.search;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    protected Queue<AState> opened;
    protected HashSet<AState> closed;
    protected boolean isCostConsidered;
    public BreadthFirstSearch(String name) {
        super(name);
    }

    public BreadthFirstSearch() {
        super("BreadthFirstSearch");
        //### initializeDataStructures();
    }

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
            closed.add(currentState);//###Maybe removeMax
            if (currentState.equals(goalState)) {
                NumberOfNodesEvaluated=closed.size();
                return new Solution(currentState);//###OR SOLUTION
            }

            //Expand the Node by getting his successors
            ArrayList<AState> successors = searchable.getAllPossibleState(currentState);
            for (AState successorState : successors) {
                if(closed.contains(successorState))
                {
                    continue;
                }
                else if (!opened.contains(successorState)) {
                    //apply the cost of arrival to successorState

                    //########NEED TO FILL########///

                    if(isCostConsidered) {
                        successorState.setCost(successorState.getCost() + currentState.getCost());
                    }
                    //Update successorState parent to be currentState
                    successorState.setParent(currentState);

                    opened.add(successorState);
                }
                if(isCostConsidered) {
                   /* if (####the current path is shorter then the previous one###) {
                        //###HOW DO I KNOW HOW TO CALCULATE THAT? IF THE PREVIOUS ONE OVERRODE THE DIRECTIONAL STEP###//
                        //add to open if it's not in there
                        if (!opened.contains(successorState)) {
                            opened.add(successorState);
                        }
                    }
                    */
                }
                    //###UPDATE successorState WITH THE SHORTEST PATH###

                    //########NEED TO FILL########///
                }
            }
        return null;
    }

}
