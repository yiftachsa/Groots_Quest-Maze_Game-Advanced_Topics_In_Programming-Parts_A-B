package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    private Maze maze;
    //###NEED TO CHANGE THOSE
    private static int DIRECTIONAL_STRAIGHT_MOVE_COST  = 10;
    private static int DIRECTIONAL_DIAGONAL_MOVE_COST  = 14; //equivalent to sqrt(2) - according to pythagoras theorem

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

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
        boolean directionalState=true;
        CheckNeighborsVertically(mazeState, allPossibleStates, verticalStates,directionalState);
        CheckNeighborsHorizontally(mazeState, allPossibleStates, horizontalStates,directionalState);

        directionalState=false;
        while (verticalStates.size()>0)
        {
            CheckNeighborsHorizontally((MazeState)verticalStates.get(0), allPossibleStates ,allPossibleStates ,directionalState);
            verticalStates.remove(0);
        }

        while (horizontalStates.size()>0)
        {
            CheckNeighborsVertically((MazeState)horizontalStates.get(0), allPossibleStates ,allPossibleStates,directionalState);
            horizontalStates.remove(0);
        }
        return  allPossibleStates;
    }

    private void CheckNeighborsVertically(MazeState mazeState , ArrayList<AState> states, ArrayList<AState> verticalStates, boolean directionalState){
        /*up*/
        Position positionUp = new Position(mazeState.getCurrentPosition().getRowIndex()-1,mazeState.getCurrentPosition().getColumnIndex());
        addLegalState(mazeState, states, verticalStates, positionUp , directionalState);
        /*down*/
        Position positionDown = new Position(mazeState.getCurrentPosition().getRowIndex()+1,mazeState.getCurrentPosition().getColumnIndex());
        addLegalState(mazeState, states, verticalStates, positionDown , directionalState);
    }

    private void CheckNeighborsHorizontally(MazeState mazeState , ArrayList<AState> states, ArrayList<AState> horizontalStates , boolean directionalState){
        /*right*/
        Position positionRight = new Position(mazeState.getCurrentPosition().getRowIndex(),mazeState.getCurrentPosition().getColumnIndex()+1);
        addLegalState(mazeState, states, horizontalStates, positionRight , directionalState);
        /*left*/
        Position positionLeft = new Position(mazeState.getCurrentPosition().getRowIndex(),mazeState.getCurrentPosition().getColumnIndex()-1);
        addLegalState(mazeState, states, horizontalStates, positionLeft , directionalState);

    }

    private void addLegalState(MazeState mazeState, ArrayList<AState> states,ArrayList<AState> directionalStates, Position position, boolean directionalState) {
        if (this.maze.isLegalMove(position)) {
            /*maybe need deep copy*/
            Maze newMaze = mazeState.getMaze();
//            newMaze.setValue(position, 2);
            //###If directionalStates == false so not a direct move. need to update the cost!!!!###
            MazeState newMazeState = new MazeState(newMaze, position);
            if (!states.contains(newMazeState)) {
                states.add(newMazeState);
                if(directionalState == true){
                    directionalStates.add(newMazeState);
                    //update cost to be suitable for straight directional step
                    //###NEED TO CHECK IF THIS IS CORRECT
                    //###MAYBE THERE IS NO NEED TO ADD THE PARENT COST,ONLY THE DIRECTION OF WHICH THE MOVE WAS MADE
                    newMazeState.setCost(DIRECTIONAL_STRAIGHT_MOVE_COST);
                } else {//###MAYBE THE ELSE SHOULD BE HERE(OR ALSO  HERE)
                    newMazeState.setCost(DIRECTIONAL_DIAGONAL_MOVE_COST);
                }
            } else { //###MAYBE SHOULD NOT BE ELSE
                //###CHECK IF THE CURRENT COST IS LOWER THEN PREVIOUS COST AND IF SO UPDATE THE COST(IN A DIAGONAL MOVE)
            }
        }
    }



}
