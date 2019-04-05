package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    /**
     * Return the start state of the problem
     * @return - AState
     */
    AState getStartState();

    /**
     * Return the goal state of the problem
     * @return - AState
     */
    AState getGoalState();

    /**
     * Returns a list containing all the possible states for the next move
     * @param state - AState
     * @return - ArrayList<AState>
     */
    ArrayList<AState> getAllPossibleState(AState state);

}
