package algorithms.search;

import java.util.ArrayList;

public class Solution {

    AState solutionState;

    public ArrayList<AState> getSolutionPath(){
        ArrayList<AState> solutionPath = new ArrayList<>();
        AState parentState = solutionState.getParent();
        while (parentState != null){
            //inserting to the start of the list
            solutionPath.add(0, parentState);
            parentState = parentState.getParent(); //"advancing" backwards
        }
        return solutionPath;
    }
}
