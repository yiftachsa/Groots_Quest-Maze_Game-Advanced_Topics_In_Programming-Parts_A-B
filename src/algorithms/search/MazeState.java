package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.Objects;

public class MazeState extends  AState implements Serializable {

    private Maze maze;
    private Position currentPosition;

    /**
     * Constructor
     * @param parent - AState
     * @param cost - double
     * @param maze - Maze
     * @param currentPosition - Position
     */
    public MazeState(AState parent, double cost, Maze maze, Position currentPosition) {
        super(parent, cost);
        this.maze = maze;
        this.currentPosition = currentPosition;
    }

    /**
     * Constructor
     * @param maze - Maze
     * @param currentPosition - Position
     */
    public MazeState(Maze maze, Position currentPosition) {
        super(null, 0);
        this.maze = maze;
        this.currentPosition = currentPosition;
    }

    /**
     * Returns the maze of this state
     * @return - Maze
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Returns the current position of this state
     * @return - Position
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String toString() {
        return "MazeState{" +
                "currentPosition=" + currentPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeState mazeState = (MazeState) o;
        return ((this.currentPosition.equals(mazeState.currentPosition)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition);
    }
}