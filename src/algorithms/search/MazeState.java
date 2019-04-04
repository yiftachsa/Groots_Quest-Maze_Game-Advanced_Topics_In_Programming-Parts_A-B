package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.Objects;

public class MazeState extends  AState {
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