package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
//###TODO: Maybe remove this constructor as it is unused throughout the program
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
