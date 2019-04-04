package algorithms.search;

import java.lang.reflect.Array;
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
                NumberOfNodesEvaluated = closed.size();
                return new Solution(currentState);//###OR SOLUTION
            }

            //Expand the Node by getting his successors
            ArrayList<AState> successors = searchable.getAllPossibleState(currentState);
            for (AState successorState : successors) {
                if (closed.contains(successorState)) {
                    continue;
                } else if (!opened.contains(successorState)) {
                    //apply the cost of arrival to successorState

                    //########NEED TO FILL########///

                    if (isCostConsidered) {
                        successorState.setCost(successorState.getCost() + currentState.getCost());
                    }
                    //Update successorState parent to be currentState
                    successorState.setParent(currentState);

                    opened.add(successorState);
                } else if (isCostConsidered) {
                    //successorState is in opened
                    //AState[] opendedArray= new opened.toArray();
                    /*for (int i = 0; i < opened.size(); i++) {
                        if (successorState.equals(opened.))
                    }*/
                  /*  boolean alreadyFound = false;
                    Iterator priorityQueueIter = opened.iterator();
                    //for (AState openedSuccessorState: opened ) {
                    while (!alreadyFound && priorityQueueIter.hasNext()){
                        AState openedSuccessorState = (AState)priorityQueueIter.next();
                        if (alreadyFound){
                            break;
                        }
                        if (successorState.equals(openedSuccessorState)){
                            alreadyFound = true;
                            if (successorState.getCost() < openedSuccessorState.getCost()){
                                opened.remove(openedSuccessorState);
                                successorState.setParent(currentState);
                                successorState.setCost(successorState.getCost() + currentState.getCost());
                                if (!opened.contains(successorState)) {
                                    opened.add(successorState);
                                }
                            }
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
