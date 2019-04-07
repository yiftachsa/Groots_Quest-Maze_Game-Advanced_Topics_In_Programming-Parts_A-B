package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    /**
     * Constructor
     **/
    public EmptyMazeGenerator() {
    }


    @Override
    public Maze generate(int row, int column) {
        return super.generateEmptyMaze(row, column);
    }
}
