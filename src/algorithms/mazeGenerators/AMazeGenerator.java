package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

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
