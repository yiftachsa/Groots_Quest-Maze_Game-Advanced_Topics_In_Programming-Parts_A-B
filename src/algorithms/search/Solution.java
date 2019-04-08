package algorithms.search;

import java.util.ArrayList;

public class Solution {

    /**
     * The last state in the solution
     */
    AState solutionState;

    /**
     * Constructor
     * @param solutionState - AState - the last state in the solution
     */
    public Solution(AState solutionState) {
        this.solutionState = solutionState;
    }

    public Solution() {
        this.solutionState=null;
    }

    /**
     * Returns a list of all the states that lead to the final state and together to the solution
     * @return - ArrayList<AState>
     */
    public ArrayList<AState> getSolutionPath(){
        ArrayList<AState> solutionPath = new ArrayList<>();
        if (solutionState != null){
            AState parentState = solutionState.getParent();
            solutionPath.add(0, solutionState);
            while (parentState != null) {
                //inserting to the start of the list
                solutionPath.add(0, parentState);
                parentState = parentState.getParent(); //"advancing" backwards
            }
        }
        return solutionPath;
    }

}
