package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

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
            System.out.println(exception.getMessage());
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
