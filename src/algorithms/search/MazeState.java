package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class MazeState extends  AState{
    private Maze maze;
    private Position currentPosition;

    public MazeState(AState parent, double cost, Maze maze, Position currentPosition) {
        super(parent, cost);
        this.maze = maze;
        this.currentPosition = currentPosition;
    }
    public MazeState(Maze maze, Position currentPosition) {
        super(null, 0);
        this.maze = maze;
        this.currentPosition = currentPosition;
    }

    public Maze getMaze() {
        return maze;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }
}
