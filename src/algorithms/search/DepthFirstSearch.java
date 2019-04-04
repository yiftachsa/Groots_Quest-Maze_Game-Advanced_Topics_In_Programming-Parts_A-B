package algorithms.search;

import java.util.*;

import java.util.concurrent.ArrayBlockingQueue;


public class DepthFirstSearch extends ASearchingAlgorithm{

    private Stack<AState> colored;
    private HashSet<AState> discovered;

    private boolean isCostConsidered;

    public DepthFirstSearch() {
        super("DepthFirstSearch");
    }

    /*
1  procedure DFS-iterative(G,v):
2      let S be a stack
3      S.push(v)
4      while S is not empty
5          v = S.pop()
6          if v is not labeled as discovered:
7              label v as discovered
8              for all edges from v to w in G.adjacentEdges(v) do
9                  S.push(w)
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



