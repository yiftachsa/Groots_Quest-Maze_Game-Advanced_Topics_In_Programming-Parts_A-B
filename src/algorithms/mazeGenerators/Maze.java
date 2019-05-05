package algorithms.mazeGenerators;
import java.io.Serializable;
import java.util.Arrays;

public class Maze implements Serializable {

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
        if(row>0 && column>0)
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
     * Constructor - build maze from byte [].
     * @param bytes
     */
    public Maze(byte[] bytes) {
        //TODO: recive the same format as outputed by toByteArray and initialize the fields.
        byte[] mazeByteArray = new byte[bytes.length - (6*4)];
        for (int i = 0; i < mazeByteArray.length; i++) {
            mazeByteArray[i] = bytes[i];
        }
        byte[] detailsByteArray = new byte[(6*4)];
        for (int i = 0; i < (6*4); i++) {
            detailsByteArray[i] = bytes[i+mazeByteArray.length];
        }
        /**
         * mazeDetails = {rows, columns, startRow,startColumn,goalRow,goalColumn};
         */
        int[] mazeDetails = getMazeDetailsFromByte(detailsByteArray);

        maze=new int [mazeDetails[0]][mazeDetails[1]]; //rows, columns
        startPosition = new Position(mazeDetails[2],mazeDetails[3]); //startRow, startColumn
        goalPosition = new Position(mazeDetails[4],mazeDetails[5]); //goalRow,goalColumn

        recreateMazeFromByte(mazeByteArray);
    }

    /**
     * Recreates Maze From Byte Array
     * @param mazeByteArray - byte []
     */
    private void recreateMazeFromByte(byte[] mazeByteArray) {
        int [] oneDimensionalMaze = new int[(maze.length)*(maze[0].length)];
        int currentDigit = 0;
        int lastIndex=0;
        for (int i = 0; i < mazeByteArray.length; i++) {
            int convertedCounter = mazeByteArray[i];
            for (int j = 0; j < convertedCounter; j++) {
                oneDimensionalMaze[lastIndex] = currentDigit;
                lastIndex ++;
            }
            currentDigit = (currentDigit + 1)%2;//good good

        }
        lastIndex = 0;
        for (int row = 0; row < maze.length; row++) {
            for (int column = 0; column < maze[0].length; column++) {
                maze[row][column] = oneDimensionalMaze[lastIndex++];
            }
        }
    }

    /**
     *
     * @param detailsByteArray
     * @return
     */
    private int[] getMazeDetailsFromByte(byte[] detailsByteArray) {
        /**
         * mazeDetails = {rows, columns, startRow,startColumn,goalRow,goalColumn};
         */
        int[] mazeDetails = new int[6];
        for (int i = 0; i < 6; i++) {
            String stringByteValue = "";
            for (int j = 0; j < 4; j++) {
                String binaryString = String.format("%8s" , Integer.toBinaryString(detailsByteArray[(i*4)+j] & 0xFF)).replace(' ', '0');
                stringByteValue += binaryString;
                //stringByteValue += detailsByteArray[(i*4)+j];//???
            }
            int binaryvalue = Integer.parseInt(stringByteValue, 2);
            mazeDetails[i] = binaryvalue;
        }
        return mazeDetails;
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
        this.maze = maze;
    }

    /**
     * Receives a Position and an int value and update the maze's value
     * at the given position to be the value given.
     * @param position - Position
     * @param value - int
     */
    public void setValue(Position position, int value){
        if (position.getRowIndex() >= 0 && position.getRowIndex() < this.maze.length && position.getColumnIndex() >= 0 && position.getColumnIndex() < this.maze[0].length){
            this.maze[position.getRowIndex()][position.getColumnIndex()] = value;
        }
    }

    /**
     * Receives row and column indexes and an int value and update the maze's value
     * at the given coordinates to be the value given.
     * @param row - int
     * @param column - int
     * @param value - int
     */
    public void setValue(int row, int column, int value){
        if (row >= 0 && row < this.maze.length && column >= 0 && column < this.maze[0].length) {
            this.maze[row][column] = value;
        }
    }

    /**
     * Receives a Position and Returns the maze's value at the given position
     * @param position - Position
     * @return - int
     */
    public int getValue(Position position) {
        if (position.getRowIndex() >= 0 && position.getRowIndex() < this.maze.length && position.getColumnIndex() >= 0 && position.getColumnIndex() < this.maze[0].length) {
            return maze[position.getRowIndex()][position.getColumnIndex()];
        }
        return -1;
    }

    /**
     * Receives row and column indexes and maze's value at the given coordinates
     * @param row  - int
     * @param column - int
     * @return - int
     */
    public int getValue(int row, int column) {
        if (row >= 0 && row < this.maze.length && column >= 0 && column < this.maze[0].length) {
            return maze[row][column];
        }
        return -1;
    }

    /**
     * Receives a Position and Sets it as the start position
     * @param startPosition - Position
     */
    public void setStartPosition(Position startPosition) {
        if (startPosition.getRowIndex() >= 0 && startPosition.getRowIndex() < this.maze.length && startPosition.getColumnIndex() >= 0 && startPosition.getColumnIndex() < this.maze[0].length) {
            this.startPosition = startPosition;
        }
    }

    /**
     * Receives a Position and Sets it as the goal position
     * @param goalPosition - Position
     */
    public void setGoalPosition(Position goalPosition) {
        if (goalPosition.getRowIndex() >= 0 && goalPosition.getRowIndex() < this.maze.length && goalPosition.getColumnIndex() >= 0 && goalPosition.getColumnIndex() < this.maze[0].length) {
            this.goalPosition = goalPosition;
        }
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

        if (rowsCount == 1 && columnsCount == 1)
        {
            this.startPosition.setX(0);
            this.startPosition.setY(0);
            this.goalPosition.setX(0);
            this.goalPosition.setY(0);
        } else {
            this.startPosition.setX((int) Math.floor(Math.random() * Math.floor(rowsCount)));
            this.startPosition.setY((int) Math.floor(Math.random() * Math.floor(columnsCount)));
            do {
                this.goalPosition.setX((int) Math.floor(Math.random() * Math.floor(rowsCount)));
                this.goalPosition.setY((int) Math.floor(Math.random() * Math.floor(columnsCount)));
            } while (startPosition.equals(goalPosition));
        }
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

    /**
     * convert maze details to byte array and implement the algorithm that they suggested.
     * @return
     */
    public byte[] toByteArray(){
        String[] mazeByteStringArray = getMazeStrings();

        byte[] mazeByteArray = new byte[mazeByteStringArray.length + (6*4)];

        AddMazeByteValues(mazeByteStringArray, mazeByteArray);

        AddMazeByteDetails(mazeByteArray);

        return mazeByteArray;
    }

    /**
     * adds the values of the maze from mazeByteStringArray to mazeByteArray
     * @param mazeByteStringArray
     * @param mazeByteArray
     */
    private void AddMazeByteValues(String[] mazeByteStringArray, byte[] mazeByteArray) {
        for (int i = 0; i < mazeByteStringArray.length; i++) {
            int intValue = Integer.parseInt(mazeByteStringArray[i]);
            byte byteValue = (byte)intValue; //FIXME: check
            mazeByteArray[i] = byteValue;
        }
    }

    /**
     * Adds all the maze details to the given byte array. assumes that there is at least 6 free entries.
     *
     * @param mazeByteArray - byte[]
     */
    private void AddMazeByteDetails(byte[] mazeByteArray) {
        int rows = maze.length;
        int columns = maze[0].length;
        int startRow = startPosition.getRowIndex();
        int startColumn = startPosition.getColumnIndex();
        int goalRow = goalPosition.getRowIndex();
        int goalColumn = goalPosition.getColumnIndex();

        int[] mazeDetails = {rows, columns, startRow,startColumn,goalRow,goalColumn};
        int counter = (mazeByteArray.length - (6*4));
        //From int TO byte
        for (int i = 0; i < mazeDetails.length; i++) {
            String byteString = Integer.toBinaryString(mazeDetails[i]);
            while (byteString.length()<32){
                byteString = "0" + byteString;
            }
            for (int j = 0; j < 32; j= j+8) {
                String stringByte = byteString.substring(j, j+8);
                int intValue = Integer.parseInt(stringByte,2);
                byte byteValue = (byte)intValue;
                mazeByteArray[counter++] = byteValue;
            }

        }
    }

    /**
     * Converts the maze to a string array, in which the values represents the sequences of the maze's values.
     * @return - String[]
     */
    private String[] getMazeStrings() {
        String mazeByteString = ""; //byte representation of the maze
        int count = 0;
        int lastDigit = 0;
        //Beginning with zero
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if(maze[i][j] == lastDigit){
                    count++;
                    if (count == 256){
                        mazeByteString += "255,0,";
                        count = 1;
                    }
                }else {
                    lastDigit = maze[i][j];
                    mazeByteString += count + ",";
                    count = 1;
                }
            }
        }
        mazeByteString += count;
        return mazeByteString.split(",");
    }
}
