package algorithms.mazeGenerators;

public interface IMazeGenerator {

    /**
     * Generates and return a maze with 'row' amount of rows and 'column' amount of columns.
     * @param row - int - non negative integer
     * @param column - int - non negative integer
     * @return - Maze - New Maze
     */
    public Maze generate(int row, int column);

    /**
     * Measures the time to generate a new maze with 'row' amount of rows and 'column' amount of columns.
     * @param row - int - non negative integer
     * @param column - int - non negative integer
     * @return - long - the time to generate the Maze
     */
    public long measureAlgorithmTimeMillis(int row , int column);

}
