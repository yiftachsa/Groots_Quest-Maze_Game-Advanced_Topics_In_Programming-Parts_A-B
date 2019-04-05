package algorithms.mazeGenerators;


import java.util.PrimitiveIterator;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{

    /**
     * Describes the direction of a move
     */
    enum moveType {right, left, up, down};


    @Override
    public Maze generate(int row, int column) {
        Maze maze= super.generateEmptyMaze(row , column);
        maze = PathCreate(maze);
        maze = CreateFinalMaze(maze);
        return maze;
    }

    /**
     * Generates and Returns a random moveType
     * @return - MoveType
     */
    private moveType RandomMove(){
        int moveNum = (int)Math.floor(Math.random() * Math.floor(4));
        return moveType.values()[moveNum];
    }

    /**
     * Receives a Move and a Position and returns the resulting position
     * @param move - moveType
     * @param currentPosition - Position - the current position
     * @return - Position - the resulting position
     */
    private  Position Move (moveType move, Position currentPosition){
        Position newPosition = new Position();
        if (move == moveType.right){
            newPosition.setX(currentPosition.getRowIndex());
            newPosition.setY(currentPosition.getColumnIndex() + 1);
        }else if (move == moveType.left){
            newPosition.setX(currentPosition.getRowIndex());
            newPosition.setY(currentPosition.getColumnIndex() - 1);
        } else if (move == moveType.up){
            newPosition.setX(currentPosition.getRowIndex() - 1);
            newPosition.setY(currentPosition.getColumnIndex());
        }else{
            newPosition.setX(currentPosition.getRowIndex() + 1);
            newPosition.setY(currentPosition.getColumnIndex());
        }
        return newPosition;

    }

    /**
     * Calculate the amount of steps that will be made AT MOST in the search of the goal state.
     * A function of the given maze's size.
     * @param maze - Maze
     * @return - int
     */
    private int goalFunction(Maze maze)
    {
        return (int)Math.pow(maze.getColumnLength()+maze.getRowLength(), 0.75);
    }

    /**
     * Creates a path in an empty maze between the start of the given maze to it's goal.
     * Recursive wrapper function
     * @param maze - Maze - an empty maze
     * @return - Maze - a maze with a marked solution path
     */
    private Maze PathCreate (Maze maze){
        rPathCreate(maze, maze.getStartPosition(), 0);
        return maze;
    }

    /**
     * Searches for a path between currentPosition and the given maze's goal position
     * using a maze path search recursive algorithm.
     * Recursive function
     * @param maze - Maze
     * @param currentPosition - Position
     * @param step - int
     * @return - Maze - a maze with a marked solution path
     */
    private boolean rPathCreate(Maze maze, Position currentPosition , int step) {
        if (currentPosition.equals(maze.getGoalPosition())){ //goal reached
            maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(),2);
            return true;
        }
        if (!IsLegalMove(maze, currentPosition)){
            //The move isn't legal
            return false;
        }
        if (step == goalFunction(maze)){ //the number of max steps allowed has been reached
            //set the final step as the goal position
            maze.setGoalPosition(currentPosition);
            maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(),2);
            return true;
        }
        //currentPosition is legal
        maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(), 3); //Mark cell as "Visited"
        //Generate the next random move
        moveType move = RandomMove();
        Position nextPosition = Move(move, currentPosition);
        /*
         * Recursively search for a path in four directions,
         * starting with the random move that was generated.
        */
        if (rPathCreate(maze, nextPosition , step+1)){
            maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(),2);
            return true;
        }
        int count = 0;
        while (count < 3) {
            move = moveType.values()[(move.ordinal() + 1) % 4];
            nextPosition = Move(move, currentPosition);
            if (rPathCreate(maze, nextPosition, step+1)) {
                maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(), 2);
                return true;
            }
            count++;
        }
        //No path was found
        maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(), 0);
        return false;
    }

    /**
     * Returns true if a Position is within the maze's boundaries
     * and if the move is not to a previously visited cell.
     * @param maze - Maze
     * @param position - Position
     * @return - boolean
     */
    private boolean IsLegalMove(Maze maze, Position position) {
        if (position.getRowIndex() >= maze.getRowLength() || position.getRowIndex() < 0 || position.getColumnIndex() >= maze.getColumnLength() || position.getColumnIndex() < 0){
            return false;
        }
        if (maze.getValue(position.getRowIndex(),position.getColumnIndex()) == 3){
            return false;
        }
        return true;
    }

    /**
     * Returns a Maze with random values and a passage between the start and the goal
     * @param maze - Maze
     * @return - Maze
     */
    private Maze CreateFinalMaze(Maze maze){
        for (int i = 0; i < maze.getRowLength(); i++) {
            for (int j = 0; j < maze.getColumnLength(); j++) {
                if (maze.getValue(i,j) != 2){
                    maze.setValue(i, j, (int)Math.floor(Math.random() * Math.floor(2)));
                }else {
                    maze.setValue(i, j, 0);
                }
            }
        }
        return maze;
    }
}
