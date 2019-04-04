package algorithms.mazeGenerators;
import java.lang.Exception;
import java.util.Arrays;
import java.util.Random;

public class Maze {

    private int [][] maze;
    private Position startPosition;
    private Position goalPosition;

    /**
     * constructor
      * @param row - int - non negative integer
     * @param column - int - non negative integer
     */
    public Maze(int row, int column)
    {
        if(row>0 || column>0)
        {
            maze=new int [row][column];
            startPosition=new Position();
            goalPosition=new Position();
            generatePositions(row, column);
        }
        else
        {
            throw new IllegalArgumentException("invalid arguments accept only positive integers");
        }
    }

    /**
     *Returns the maze as a two-dimensional int array
     * @return - int[][]
     */
    public int[][] getMaze() {
        return maze;
    }

    /**
     * Returns the number of rows in the maze
     * @return  - int
     */
    public int getRowLength() {
        return maze.length;
    }

    /**
     * Returns the number of columns in the maze
     * @return
     */
    public int getColumnLength() {
        return maze[0].length;
    }

    /**
     *Returns the start Position of the maze
     * @return
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     *
     * @return
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /* need to add input checks*/

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void setValue(Position position, int value){
        this.maze[position.getRowIndex()][position.getColumnIndex()] = value;
    }
    public void setValue(int row, int column, int value){
        this.maze[row][column] = value;
    }
    public int getValue(Position position) {
        return maze[position.getRowIndex()][position.getColumnIndex()];
    }
    public int getValue(int row, int column) {
        return maze[row][column];
    }
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }
    public boolean isLegalMove(Position position) {
        if (position.getRowIndex() >= this.getRowLength() || position.getRowIndex() < 0 || position.getColumnIndex() >= this.getColumnLength() || position.getColumnIndex() < 0) {
            return false;
        }
        if (this.getValue(position) == 1){
            return false;
        }
        return true;
    }
    public void print() {
        System.out.println("Maze: ");
        for (int i = 0; i < this.maze.length; i++) {

            for (int j = 0; j < this.maze[0].length; j++) {
                if(i==startPosition.getRowIndex()&& j == startPosition.getColumnIndex())
                {
                    System.out.print("S");
                }
                else if(i==goalPosition.getRowIndex()&& j == goalPosition.getColumnIndex())
                {
                    System.out.print("E");
                }
                else{
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Maze{" +
                "maze=" + Arrays.toString(maze) +
                ", startPosition=" + startPosition +
                ", goalPosition=" + goalPosition +
                '}';
    }


    private void generatePositions(int row, int column){
        this.startPosition.setX((int)Math.floor(Math.random() * Math.floor(row)));
        this.startPosition.setY((int)Math.floor(Math.random() * Math.floor(column)));
        do {
            this.goalPosition.setX((int)Math.floor(Math.random() * Math.floor(row)));
            this.goalPosition.setY((int)Math.floor(Math.random() * Math.floor(column)));
        }while (startPosition.equals(goalPosition));
    }

}
