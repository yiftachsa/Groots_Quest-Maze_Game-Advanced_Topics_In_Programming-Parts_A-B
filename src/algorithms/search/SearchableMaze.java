package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    private Maze maze;
    /**
     * Terrain cost analysis - calculates distance for a straight move
     */
    private final int DIRECTIONAL_STRAIGHT_MOVE_COST  = 10;
    /**
     * Terrain cost analysis - calculates distance for a diagonal move
     * Equivalent to sqrt(200) - according to pythagoras theorem
     */
    private final int DIRECTIONAL_DIAGONAL_MOVE_COST  = 14;

    /**
     * Constructor
     * @param maze - Maze - the maze that will be searched upon
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Returns this maze
     * @return - Maze
     */
    public Maze getMaze() {
        return maze;
    }


    @Override
    public AState getStartState() {
        return new MazeState(this.maze, this.maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(this.maze, this.maze.getGoalPosition());
    }


    @Override
    public ArrayList<AState> getAllPossibleState(AState state) {
        ArrayList<AState> allPossibleStates= new ArrayList<>();
        ArrayList<AState> verticalStates = new ArrayList<>();
        ArrayList<AState> horizontalStates = new ArrayList<>();
        MazeState mazeState = (MazeState)state;
        //Check Neighbors in straight Moves
        boolean isStraightDirectionalMove=true;
        CheckNeighborsVertically(mazeState, allPossibleStates, verticalStates,isStraightDirectionalMove);
        CheckNeighborsHorizontally(mazeState, allPossibleStates, horizontalStates,isStraightDirectionalMove);

        //Check Neighbors in diagonal Moves
        isStraightDirectionalMove=false;

        while (verticalStates.size()>0)
        {
            CheckNeighborsHorizontally((MazeState)verticalStates.get(0), allPossibleStates ,allPossibleStates ,isStraightDirectionalMove);
            verticalStates.remove(0);
        }

        while (horizontalStates.size()>0)
        {
            CheckNeighborsVertically((MazeState)horizontalStates.get(0), allPossibleStates ,allPossibleStates,isStraightDirectionalMove);
            horizontalStates.remove(0);
        }
        return  allPossibleStates;
    }

    /**
     * Receives a maze state and adds ONLY his legal vertical neighboring positions to the list of successors
     * @param mazeState - MazeState
     * @param states - ArrayList<AState>
     * @param verticalStates - ArrayList<AState>
     * @param directionalState - boolean
     */
    private void CheckNeighborsVertically(MazeState mazeState , ArrayList<AState> states, ArrayList<AState> verticalStates, boolean directionalState){
        /*up*/
        Position positionUp = new Position(mazeState.getCurrentPosition().getRowIndex()-1,mazeState.getCurrentPosition().getColumnIndex());
        addLegalState(mazeState, states, verticalStates, positionUp , directionalState);
        /*down*/
        Position positionDown = new Position(mazeState.getCurrentPosition().getRowIndex()+1,mazeState.getCurrentPosition().getColumnIndex());
        addLegalState(mazeState, states, verticalStates, positionDown , directionalState);
    }

    /**
     * Receives a maze state and adds ONLY his legal horizontal neighboring positions to the list of successors
     * @param mazeState - MazeState
     * @param states - ArrayList<AState>
     * @param horizontalStates - ArrayList<AState>
     * @param directionalState - boolean
     */
    private void CheckNeighborsHorizontally(MazeState mazeState , ArrayList<AState> states, ArrayList<AState> horizontalStates , boolean directionalState){
        /*right*/
        Position positionRight = new Position(mazeState.getCurrentPosition().getRowIndex(),mazeState.getCurrentPosition().getColumnIndex()+1);
        addLegalState(mazeState, states, horizontalStates, positionRight , directionalState);
        /*left*/
        Position positionLeft = new Position(mazeState.getCurrentPosition().getRowIndex(),mazeState.getCurrentPosition().getColumnIndex()-1);
        addLegalState(mazeState, states, horizontalStates, positionLeft , directionalState);

    }

    /**
     * Receives a maze state and adds a new state to his successors if the position that was received is legal
     * @param mazeState - MazeState
     * @param states - ArrayList<AState>
     * @param directionalStates - ArrayList<AState>
     * @param position - Position
     * @param directionalState - boolean
     */
    private void addLegalState(MazeState mazeState, ArrayList<AState> states,ArrayList<AState> directionalStates, Position position, boolean directionalState) {
        if (this.maze.isLegalMove(position)) {
            //###TODO: maybe need a deep copy
            Maze newMaze = mazeState.getMaze();
            MazeState newMazeState = new MazeState(newMaze, position);
            if (!states.contains(newMazeState)) {
                states.add(newMazeState);
                if(directionalState){
                    directionalStates.add(newMazeState);
                    //update cost to be suitable for straight directional step
                    newMazeState.setCost(DIRECTIONAL_STRAIGHT_MOVE_COST);
                } else {
                    //update cost to be suitable for diagonal directional step
                    newMazeState.setCost(DIRECTIONAL_DIAGONAL_MOVE_COST);
                }
            }
        }
    }

}
