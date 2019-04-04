package algorithms.mazeGenerators;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.PrimitiveIterator;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{

    enum moveType {right, left, up, down};


    @Override
    public Maze generate(int row, int column) {
        Maze maze= super.generateEmptyMaze(row , column);
        maze = PathCreate(maze);
        maze = CreateFinalMaze(maze);
        return maze;
    }

    /**
     *
     * @return
     */
    private moveType RandomMove(){
        int moveNum = (int)Math.floor(Math.random() * Math.floor(4));
        return moveType.values()[moveNum];
    }

    /**
     * Receives a move and a position and returns the resulting position
     * @param move
     * @param currentPosition
     * @return
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

    private int goalFunction(Maze maze)
    {
        return (int)Math.pow(maze.getColumnLength()+maze.getRowLength(), 0.5);
    }

    private Maze PathCreate (Maze maze){
        System.out.println("goal "+ maze.getGoalPosition() + " , " + "start " + maze.getStartPosition());
        rPathCreate(maze, maze.getStartPosition(), 0);

        return maze;
    }

    private boolean rPathCreate(Maze maze, Position currentPosition , int step) {
        if (currentPosition.equals(maze.getGoalPosition())){
            maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(),2);
            return true;
        }
        if (!IsLegalMove(maze, currentPosition)){
            return false;
        }
        if (step == goalFunction(maze)){
            maze.setGoalPosition(currentPosition);
            maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(),2);
            return true;
        }
        //currentPosition is legal
        maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(), 3); //Mark cell as "Visited"
        //moves
        moveType move = RandomMove();
        Position nextPosition = Move(move, currentPosition);

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
        maze.setValue(currentPosition.getRowIndex(),currentPosition.getColumnIndex(), 0);
        return false;
    }

    private boolean IsLegalMove(Maze maze, Position position) {
        if (position.getRowIndex() >= maze.getRowLength() || position.getRowIndex() < 0 || position.getColumnIndex() >= maze.getColumnLength() || position.getColumnIndex() < 0){
            return false;
        }
        if (maze.getValue(position.getRowIndex(),position.getColumnIndex()) == 3){
            return false;
        }
        return true;
    }

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
