package algorithms.mazeGenerators;
import java.util.Arrays;

public class Maze {

    private int [][] maze;
    private Position startPosition;
    private Position goalPosition;

    /**
     * Constructor
     * @param row - int - non negative integer
     * @param column - int - non negative integer
     * @throws IllegalArgumentException - if the row or column args are smaller than 1
     */
    public Maze(int row, int column) throws IllegalArgumentException
    {
        if (row == 1 && column == 1){
            //###TODO: Decide what to do
        }
        if(row>0 || column>0)
        {
            maze=new int [row][column];
            startPosition=new Position();
            goalPosition=new Position();
            generateRandomPositions(row, column);
        }
        else
        {
            throw new IllegalArgumentException("invalid arguments accept only positive integers");
        }
    }

    /**
     * Returns the maze as a two-dimensional int array
     * @return - int[][]
     */
    public int[][] getMaze() {
        return maze;
    }

    /**
     * Returns the number of rows in the maze
     * @return - int
     */
    public int getRowLength() {
        return maze.length;
    }

    /**
     * Returns the number of columns in the maze
     * @return - int
     */
    public int getColumnLength() {
        return maze[0].length;
    }

    /**
     * Returns the start Position of the maze
     * @return - int
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Returns the goal Position of the maze
     * @return - int
     */
    public Position getGoalPosition() {
        return goalPosition;
    }



    /**
     * Receives a maze as a two-dimensional int array
     * @param maze - int[][]
     */
    public void setMaze(int[][] maze) {
        //TODO: need to add input checks
        this.maze = maze;
    }

    /**
     * Receives a Position and an int value and update the maze's value
     * at the given position to be the value given.
     * @param position - Position
     * @param value - int
     */
    public void setValue(Position position, int value){
        this.maze[position.getRowIndex()][position.getColumnIndex()] = value;
    }

    /**
     * Receives row and column indexes and an int value and update the maze's value
     * at the given coordinates to be the value given.
     * @param row - int
     * @param column - int
     * @param value - int
     */
    public void setValue(int row, int column, int value){
        this.maze[row][column] = value;
    }

    /**
     * Receives a Position and Returns the maze's value at the given position
     * @param position - Position
     * @return - int
     */
    public int getValue(Position position) {
        return maze[position.getRowIndex()][position.getColumnIndex()];
    }

    /**
     * Receives row and column indexes and maze's value at the given coordinates
     * @param row  - int
     * @param column - int
     * @return - int
     */
    public int getValue(int row, int column) {
        return maze[row][column];
    }

    /**
     * Receives a Position and Sets it as the start position
     * @param startPosition - Position
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Receives a Position and Sets it as the goal position
     * @param goalPosition - Position
     */
    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    /**
     * Receives a Position
     * Returns true if the position is within the boundaries of the maze and
     * also the maze's value at the position doesn't indicate a wall.
     * @param position - Position
     * @return - boolean
     */
    public boolean isLegalMove(Position position) {
        if (position.getRowIndex() >= this.getRowLength() || position.getRowIndex() < 0 || position.getColumnIndex() >= this.getColumnLength() || position.getColumnIndex() < 0) {
            return false;
        }
        if (this.getValue(position) == 1){
            return false;
        }
        return true;
    }

    /**
     * Generates random start and goal Positions
     * @param rowsCount - int
     * @param columnsCount - int
     */
    private void generateRandomPositions(int rowsCount, int columnsCount){
        //###TODO: maybe add handling here for a 1-by-1 maze
        this.startPosition.setX((int)Math.floor(Math.random() * Math.floor(rowsCount)));
        this.startPosition.setY((int)Math.floor(Math.random() * Math.floor(columnsCount)));
        do {
            this.goalPosition.setX((int)Math.floor(Math.random() * Math.floor(rowsCount)));
            this.goalPosition.setY((int)Math.floor(Math.random() * Math.floor(columnsCount)));
        }while (startPosition.equals(goalPosition));
    }

    /**
     * Prints to console the maze as int[][]
     * S - indicates the Start position
     * E - indicates the goal/Exit position
     */
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

    /**
     * Print to console a colored representation of the maze
     */
    public void coloredPrint () {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex()) {//startPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex()) {//goalPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (maze[i][j] == 1) System.out.print(" " + "\u001B[45m" + " ");
                else System.out.print(" " + "\u001B[107m" + " ");
            }
            System.out.println(" " + "\u001B[107m");
        }

    }

    /**
     * Returns the Maze's values as a String
     * @return - String
     */
    @Override
    public String toString() {
        return "Maze{" +
                "maze=" + Arrays.toString(maze) +
                ", startPosition=" + startPosition +
                ", goalPosition=" + goalPosition +
                '}';
    }

}
