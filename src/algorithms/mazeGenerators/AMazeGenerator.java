package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

    private final int DEFAULT_MAZE_SIZE = 5;

    /**
     * Generates and return a maze with 'row' amount of rows and 'column' amount of columns.
     * All the values in the maze are 0, meaning the maze is empty.
     * @param row - int - non negative integer
     * @param column - int - non negative integer
     * @return - int[][] - the maze
     */
    public Maze generateEmptyMaze(int row, int column) {

        Maze maze=null;
        try
        {
            maze = new Maze(row, column);
        }catch (IllegalArgumentException exception)
        {
            maze = new Maze(DEFAULT_MAZE_SIZE, DEFAULT_MAZE_SIZE);
        }
        return maze;
    }

    @Override
    public long measureAlgorithmTimeMillis(int row, int column) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        generate(row , column);
        endTime=System.currentTimeMillis();
        return endTime-startTime;
    }
}
