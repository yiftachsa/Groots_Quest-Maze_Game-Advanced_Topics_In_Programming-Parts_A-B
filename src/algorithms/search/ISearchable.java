package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    AState getStartState();
    AState getGoalState();
    /*all possible states for the next move*/
    ArrayList<AState> getAllPossibleState(AState state);

}
